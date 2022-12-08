package timetables_sort_use_case.application_business;

/**
 * The request model used to request timetables sorting
 * Instance Attributes:
 * - timeButton: the user's time preference
 * - breakButton: the user's break preference
 */
public record TimetablesSortRequestModel(String timeButton, String breakButton) {
}
