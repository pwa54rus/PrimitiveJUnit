package main;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import annotation.After;
import annotation.Before;
import annotation.Test;

class Parser{

	private final Class testClass;
	private final long id;


	private void emptyMethod(){

	}

	private long getThreadId(){

		return id;
	}

	Parser(Class testClass, long testId){

		this.testClass = testClass;
		id = testId;
	}


	public void parse() throws Exception{

		String fileName = testClass.getName() + ".txt";

		int testsPassed = 0;
		int testsFailed = 0;

		Method[] methods = testClass.getMethods();

		try{

			FileWriter testWriter = new FileWriter(fileName, false);


			testWriter.write("Class name - " + testClass.getName() + "Class id - " + getThreadId() + "\n");

			Method before = Parser.class.getDeclaredMethod("emptyMethod");
			Method after = Parser.class.getDeclaredMethod("emptyMethod");

			Object beforeDeclaringClass = this;
			Object afterDeclaringClass = this;
			Object methodDeclaringClass = this;


			for (Method method : methods){

				if (methodDeclaringClass == this){
					methodDeclaringClass = method.getDeclaringClass().newInstance();
				}


				if (method.isAnnotationPresent(Before.class)){

						before = method;
						beforeDeclaringClass = methodDeclaringClass;
				}


				if (method.isAnnotationPresent(After.class)) {

					after = method;
					afterDeclaringClass = methodDeclaringClass;
				}

			}

			int i = 1;

			for (Method method : methods){


				String logMessage = "";

				if (method.isAnnotationPresent(Test.class)){

					Test annotation = method.getAnnotation(Test.class);

					try{

						before.invoke(beforeDeclaringClass);

						method.invoke(methodDeclaringClass);

						logMessage = "Test "+ i +" \"" + method.getName() + "\" passed\n";

						System.out.print(logMessage);
						testWriter.write(logMessage);
						testsPassed++;

						try{

							after.invoke(afterDeclaringClass);
						}
						catch(IllegalArgumentException | InvocationTargetException | IllegalAccessException  exception) {

							System.out.println("After method is incorrect");
						}

					}
					catch (InvocationTargetException e){

						Throwable throwable = e.getTargetException();

						if (throwable.getClass().equals(annotation.expectedException())){

							logMessage = "Test "+ i +" \"" + method.getName() + "\" passed\n";

							testWriter.write(logMessage);
							System.out.print(logMessage);

							testsPassed++;

							try{

								after.invoke(afterDeclaringClass);
							}
							catch(IllegalArgumentException | InvocationTargetException | IllegalAccessException exception){

								System.out.println("After method is incorrect");
							}

							continue;
						}

						logMessage = "Test "+ i +" \"" + method.getName() + "\" failed: " + throwable.getMessage() +"\n\n";

						testWriter.write(logMessage);
						System.out.print(logMessage);

						testsFailed++;
					}
					catch (IllegalAccessException | IllegalArgumentException e){

						logMessage = "Test "+ i +" \"" + method.getName() + "\" failed: " + e.getMessage() +"\n\n";
						testWriter.write(logMessage);
						System.out.print(logMessage);

						testsFailed++;
					}

					i++;
				}
			}


			String logMessage = "\nTesting finished, executed " + i + " tests\n" + "passed " + testsPassed + ", " +
					"failed " + testsFailed + " tests\n";

			testWriter.write(logMessage);
			System.out.print(logMessage);

			testWriter.flush();
		}
		catch(IOException e){

			System.out.println("File is not exist");
		}
	}
}
