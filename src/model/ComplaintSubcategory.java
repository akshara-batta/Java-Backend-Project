package model;

public enum ComplaintSubcategory {

    // ELECTRICITY
    POWER_CUT(ComplaintCategory.ELECTRICITY, "Power cut"),
    FREQUENT_POWER_FLUCTUATIONS(ComplaintCategory.ELECTRICITY, "Frequent power fluctuations"),
    LIGHTS_NOT_WORKING(ComplaintCategory.ELECTRICITY, "Lights not working"),
    FANS_NOT_WORKING(ComplaintCategory.ELECTRICITY, "Fans not working"),
    SWITCH_SOCKET_PROBLEM(ComplaintCategory.ELECTRICITY, "Switch / socket problem"),
    OTHER_ELECTRICITY(ComplaintCategory.ELECTRICITY, "Other"),

    // WATER
    NO_WATER_SUPPLY(ComplaintCategory.WATER, "No water supply"),
    LOW_WATER_PRESSURE(ComplaintCategory.WATER, "Low water pressure"),
    WATER_LEAKAGE(ComplaintCategory.WATER, "Water leakage"),
    DRINKING_WATER_PROBLEM(ComplaintCategory.WATER, "Drinking water problem"),
    WATER_QUALITY_ISSUE(ComplaintCategory.WATER, "Water quality issue"),
    OTHER_WATER(ComplaintCategory.WATER, "Other"),

    // MESS
    FOOD_QUALITY(ComplaintCategory.MESS, "Food quality"),
    MESS_CLEANLINESS_HYGIENE(ComplaintCategory.MESS, "Mess cleanliness / hygiene"),
    FOOD_SHORTAGE(ComplaintCategory.MESS, "Food shortage"),
    MENU_RELATED_ISSUE(ComplaintCategory.MESS, "Menu-related issue"),
    MESS_TIMING_ISSUE(ComplaintCategory.MESS, "Mess timing issue"),
    OTHER_MESS(ComplaintCategory.MESS, "Other"),

    // CLEANLINESS
    WASHROOM_NOT_CLEAN(ComplaintCategory.CLEANLINESS, "Washroom not clean"),
    GARBAGE_NOT_COLLECTED(ComplaintCategory.CLEANLINESS, "Garbage not collected"),
    COMMON_AREA_NOT_CLEAN(ComplaintCategory.CLEANLINESS, "Common area not clean"),
    PEST_INSECT_ISSUE(ComplaintCategory.CLEANLINESS, "Pest / insect issue"),
    OTHER_CLEANLINESS(ComplaintCategory.CLEANLINESS, "Other"),

    // HOSTEL
    ROOM_MAINTENANCE(ComplaintCategory.HOSTEL, "Room maintenance"),
    FURNITURE_PROBLEM(ComplaintCategory.HOSTEL, "Furniture problem"),
    AC_FAN_PROBLEM(ComplaintCategory.HOSTEL, "AC / fan problem"),
    BATHROOM_PROBLEM(ComplaintCategory.HOSTEL, "Bathroom problem"),
    OTHER_HOSTEL(ComplaintCategory.HOSTEL, "Other");

    private ComplaintCategory category;
    private String displayName;

    ComplaintSubcategory(ComplaintCategory category, String displayName){
        this.category = category;
        this.displayName = displayName;
    }

    public ComplaintCategory getCategory(){
        return category;
    }

    public String getDisplayName(){
        return displayName;
    }
}
