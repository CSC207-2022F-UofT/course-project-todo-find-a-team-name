package timetables_sort_use_case.application_business;

import entities.Block;
import entities.Section;
import entities.Timetable;
import entities.TimetableCourse;
import retrieve_timetable_use_case.application_business.RetrieveTimetableInputBoundary;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static java.lang.Math.abs;

/**
 * A concrete implementation of the SorterInputBoundary, used in the timetables sort use case.
 * timetables are the timetables being reordered.
 * presenter is the SorterOutputBoundary that updates in response to the user's input
 */
public class TimetablesSortInteractor implements TimetablesSortInputBoundary {

    private Timetable[] timetables;
    private final TimetablesSortOutputBoundary presenter;
    private RetrieveTimetableInputBoundary retrieveInteractor;

    public TimetablesSortInteractor(TimetablesSortOutputBoundary presenter) {
        this.presenter = presenter;
    }

    /**
     * timetableSort works by finding the TimeScore and BreakScore of each timetable (BreakScores are
     * more weighted than TimeScores), then sorting through the timetables.
     * We are able to use the sort method because Timetable implements Comparable<Timetable>
     * we then convert timetables into a TimeTableModel array using retrieveInteractor
     * to adhere to the response model
     * @param request a SorterRequestModel that contains the stringified version of TimeButton and BreakButton
     */
    @Override
    public void timetablesSort(TimetablesSortRequestModel request) {
        List<Timetable> timetableArrayList = new ArrayList<>();
        for (Timetable timetable : timetables) {
            double score = getTimeScore(timetable, request.getTimeButton());
            score += getBreakScore(timetable, request.getBreakButton());
            timetable.setScore(score);
            timetableArrayList.add(timetable);
        }

        Collections.sort(timetableArrayList);
        for (int i = 0; i < timetables.length; i++) {
            timetables[i] = timetableArrayList.get(i);
        }

        TimetableModel[] timetablesModel = new TimetableModel[timetables.length];
        for(int i = 0; i < timetables.length; i++) {
            retrieveInteractor.setTimetable(timetables[i]);
            timetablesModel[i] = retrieveInteractor.retrieveTimetable();
        }

        TimetablesSortResponseModel response = new TimetablesSortResponseModel(timetablesModel);
        presenter.prepareView(response);
    }

    /**
     * if the user wants morning classes then the time score of a timetable is largest when it is of
     * a small magnitude since it is negative, vice-versa for evening, and largest for afternoon is when
     * its closest to 2pm
     * @param timetable the timetable that we want to find the score of
     * @param timeButton the user's time preference
     * @return double, TimeScore depending on user's preference
     */
    private double getTimeScore(Timetable timetable, String timeButton) {
        double score = getRawTimeScore(timetable);
        if (timeButton.contains("morning")) {
            return (-score);
        } else if (timeButton.contains("afternoon")) {
            return -abs((score - 2));
        } else if (timeButton.contains("evening")) {
            return score;
        } else {
            return 0;
        }
    }

    /**
     *
     * @param timetable the timetable that we want to find the score of
     * @return double, average time that classes start
     */
    private double getRawTimeScore(Timetable timetable) {
        double score = 0;
        int count = 0;
        List<TimetableCourse> courses = timetable.getCourseList();
        for (TimetableCourse course : courses) {
            List<Section> sections = course.getSections();
            for (Section section : sections) {
                List<Block> blocks = section.getBlocks();
                for (Block block : blocks) {
                    score += block.getStartTime();
                    count++;
                }
            }
        }
        return score / count;
    }

    /**
     * if the user wants the commuter plan, timetables with the least number of days on campus will be at the top
     * otherwise, if they want breaks then we prioritize timetables with the least number of back to backs
     * @param timetable the timetable that we want to find the score of
     * @param breakButton the user's break preference
     * @return double, BreakScore depending on user's preference
     */
    private double getBreakScore(Timetable timetable, String breakButton) {
        if (breakButton.contains("breaks")) {
            return getGapScore(timetable);
        } else if (breakButton.contains("commuter")) {
            return getDaysScore(timetable);
        } else {
            return 0;
        }
    }

    /**
     *
     * @param timetable the timetable that we want to find the score of
     * @return double, number of back to back classes * -1000
     */
    private double getGapScore(Timetable timetable) {
        int count = 0;
        List<Double>[] table = new List[5];
        for (int i = 0; i < 5; i++) {
            table[i] = new ArrayList<>();
        }
//      sorry this one is so bad, I can't brain anymore
        List<TimetableCourse> courses = timetable.getCourseList();
        for (TimetableCourse course : courses) {
            List<Section> sections = course.getSections();
            for (Section section : sections) {
                List<Block> blocks = section.getBlocks();
                for (Block block : blocks) {
                    List<Double> tableDay = table[block.getDay()];
                    for (double start : tableDay) {
                        double end = block.getEndTime();
                        if ((start + 0.1 > end) && (end + 0.1 > start)) {
                            count++;
                        }
                    }
                    table[block.getDay()].add(block.getStartTime());
                }
            }
        }
        return count * -1000;
    }

    /**
     *
     * @param timetable the timetable we want to find the score of
     * @return number of days without classes * 1000
     */
    private int getDaysScore(Timetable timetable) {
        int[] days = {1, 1, 1, 1, 1};
        List<TimetableCourse> courses = timetable.getCourseList();
        for (int i = 0; i < courses.size(); i++) {
            List<Section> sections = courses.get(i).getSections();
            for (int j = 0; j < sections.size(); j++) {
                List<Block> blocks = sections.get(i).getBlocks();
                for (Block block : blocks) {
                    days[block.getDay()] = 0;
                }
            }
        }
        return (days[0] + days[1] + days[2] + days[3] + days[4]) * 1000;
    }

    @Override
    public void setTimetables(Timetable[] timetables) {
        this.timetables = timetables;
    }

    @Override
    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor) {
        this.retrieveInteractor = retrieveInteractor;
    }
}
