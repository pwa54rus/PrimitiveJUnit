package main;

import java.util.Vector;

public class Runner{

	public static Vector<String> tests = new Vector<>();

	public static void main(String [] args){

		int countOfTreads = 0;

		try{

			countOfTreads = Integer.parseInt(args[0]);
		}
		catch(NumberFormatException e){

			System.out.println("Count of threads incorrect. First argument should be numeric");
		}


		if ( countOfTreads <= 0){

			System.out.println("You should choose number more than zero to set threads count");
			return;
		}

		Thread[] threads = new Thread[countOfTreads];

		for(int i = 0; i < countOfTreads; i++){

			threads[i] = new Thread(){

				@Override
				public void run(){

					while(!isInterrupted()){

						try{

							String testName = tests.remove(0);
							Parser parser = new Parser(Class.forName(testName), getId());
							parser.parse();
						}

						catch (ArrayIndexOutOfBoundsException e){ }

						catch (ClassNotFoundException e) {

							System.out.println("Class name is incorrect or class is not exist");

						}

						catch(Exception e){

							e.printStackTrace();
						}
					}
				}
			};

			threads[i].start();
		}

		for(int i = 1; i < args.length; i++){

			tests.add(args[i]);
		}


		try{

			while (!tests.isEmpty()){

				Thread.sleep(500);
			}




			for(Thread thread : threads){

				thread.interrupt();
			}


			int interruptedThreadsCounter = 0;

			while(interruptedThreadsCounter != threads.length){

				if (threads[interruptedThreadsCounter].isAlive())
				{
					Thread.sleep(500);
					continue;
				}

				interruptedThreadsCounter++;
			}

		}
		catch(Exception e){

			e.printStackTrace();
		}
	}
}
