import java.io.File;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.Writer;
import java.io.IOException;
import java.util.Scanner;
import java.util.TimeZone;

public class kiwiCal {
	public static File file = null;
	public static TimeZone tz = TimeZone.getDefault();
	public static String start = "BEGIN:VCALENDAR\n", end = "END:VCALENDAR", ver = "VERSION:",
			input, summary = "SUMMARY:", location = "LOCATION:", sDate = "DTSTART:", eDate = "DTEND:",
			stringPriority = "PRIORITY:", timezone = "TIMEZONE:", classification = "CLASS:";
	public static int intPriority; // 0 undefined, 1-4 high, 5 meduim, 6-9 low
	public static Scanner scan = new Scanner(System.in);
	public static double verNum = 2.0;
	
	/* Greet user and begins program*/
	public static void main(String[] args) {
		System.out.println("Welcome to Team KiwiFruit's iCalendar Project");
		createEvent();
		createICSFile();
	}
	
	/* Grabs the information needed to create an event via user input*/
	public static void createEvent() {
		// Get the name of the event
		System.out.print("Name of the event: ");
		input = scan.nextLine();
		summary = summary.concat(input + "\n");

		// Get the location of the event
		System.out.print("Location: ");
		input = scan.nextLine();
		location = location.concat(input + "\n");
		
		// Get the Priority of the event and classifies it as low/med/high
		System.out.print("Priority of Event: ");
		input = scan.nextLine();
		intPriority = Integer.parseInt(input);
		if(intPriority == 0)
			stringPriority = stringPriority.concat("undefined" + "\n");
		if(intPriority >= 1 && intPriority <= 4)
			stringPriority = stringPriority.concat("high" + "\n");
		if(intPriority == 5)
			stringPriority = stringPriority.concat("medium" + "\n");
		if(intPriority >= 6 && intPriority <= 9)
			stringPriority = stringPriority.concat("low" + "\n");
	
		// Get the classification of the event (public, private, confidential)
		System.out.print("Classification (public, private, confidential): ");
		input = scan.nextLine();
		if(input.length() < 1)
			classification = classification.concat("public\n"); //public is default, if nothing is entered
		else
			classification = classification.concat(input + "\n");
		
		boolean temp = false;
		String date = "", time = "";
		System.out.print("Start Date (yyyy/mm/dd): ");
		while ((date = promptDate()) == null) {
			System.out.print("Start Date (yyyy/mm/dd): ");
		}
		
		System.out.print("Start time (hh:mm): ");
		while ((time = promptTime()) == null) {
			System.out.print("Start time (hh:mm): ");
		}
		sDate = sDate.concat(date + time);
		
		System.out.print("End Date (yyyy/mm/dd): ");
		while ((date = promptDate()) == null) {
			System.out.print("End Date (yyyy/mm/dd): ");
		}
		
		System.out.print("End time (hh:mm): ");
		while ((time = promptTime()) == null) {
			System.out.print("End time (hh:mm): ");
		}
		eDate = eDate.concat(date + time);
	}
	
	/* Prompts the user for date of the event*/
	public static String promptDate() {
		String temp = "";
		input = scan.nextLine();
		if(input.length() != 10) {
			System.out.println("Invalid input. Try again.");
			return null;
		}
		temp = temp.concat(input.substring(0, 4) + input.substring(5, 7) + input.substring(8, 10));
		return temp;
	}
	
	/*prompts user for the time of the event*/
	public static String promptTime() {
		String temp = "";
		input = scan.nextLine();
		if(input.length() != 5) {
			System.out.println("Invalid input. Try again.");
			return null;
		}
		temp = temp.concat("T" + input.substring(0, 2) + input.substring(3, 5) + "00\n");
		return temp;
	}
	
	/* Creates the .ics file for the user to use*/
	public static void createICSFile() {
		timezone = timezone.concat(tz.getDisplayName() + "\n"); // gets timezone of device
		ver = ver.concat(verNum + "\n");
		try {
			// Create a new file
			file = new File("ics314.ics");

			// Writing to the ics file
			// FileWriter will append to the file
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			
			// Writing to the file
			output.write(start);
			output.write(ver);
			output.write(timezone);
			
			output.write("BEGIN:VEVENT\n");
			output.write(sDate);
			output.write(eDate);
			output.write(location);
			output.write(summary);
			output.write(stringPriority);
			output.write(classification);
			output.write("END:VEVENT\n");
			
			// Footer
			output.write(end);
			output.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
