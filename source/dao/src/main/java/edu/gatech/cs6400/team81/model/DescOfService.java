package edu.gatech.cs6400.team81.model;

public enum DescOfService {
	ADDED_TO_WAITLIST("Added to WaitList"), 
	CHANGED_WAITLIST_POS("Changed WaitList Position"),
	REMOVED_FROM_WAITLIST("Removed from WaitList"),
	FAMILY_ROOM_CHECK_IN("Sheleter - Family Room Check In"),
	BUNK_CHECK_IN("Shelter - Bunk Check In"),
	SOUP_KITCHEN_CHECK_IN("Soup Kitchen Check In"),
	FOOD_PANTRY_CHECK_IN("Food Pantry Check In"),
	CHANGED_CLIENT_DATA("Client data has changed.  See notes for details."),
	ADDED_CLIENT("Added new client.");
	

	private String desc;
	
	private DescOfService(String desc) {
		this.desc = desc;
	}
	
	@Override
	public String toString() {
		return desc;
	}
}
