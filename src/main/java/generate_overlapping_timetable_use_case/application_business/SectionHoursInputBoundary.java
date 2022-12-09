package generate_overlapping_timetable_use_case.application_business;


import retrieve_timetable_use_case.application_business.SectionModel;

/**
 * An input boundary that's used for getting the number of hours in a section.
 **/
public interface SectionHoursInputBoundary {

    /**
     * calculate the length, in Hours, of a section.
     **/
    Double calculateHoursOfSection(SectionModel section);

}
