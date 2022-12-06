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

    @Override
    public void timetablesSort(TimetablesSortRequestModel request) {
        List<Timetable> timetableArrayList = new ArrayList<>();
        for (int i = 0; i < timetables.length; i++) {
            double score = getTimeScore(timetables[i], request.getTimeButton());
            score += getBreakScore(timetables[i], request.getBreakButton());
            timetables[i].setScore(score);
            timetableArrayList.add(timetables[i]);
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

    private double getRawTimeScore(Timetable timetable) {
        double score = 0;
        int count = 0;
        List<TimetableCourse> courses = timetable.getCourseList();
        for (int i = 0; i < courses.size(); i++) {
            List<Section> sections = courses.get(i).getSections();
            for (int j = 0; j < sections.size(); j++) {
                List<Block> blocks = sections.get(i).getBlocks();
                for (int k = 0; k < blocks.size(); k++) {
                    score += blocks.get(i).getStartTime();
                    count++;
                }
            }
        }
        return score / count;
    }

    private double getBreakScore(Timetable timetable, String breakButton) {
        return 0;
    }

    /**
     *
     * @param timetables updates the timetables used by the interactor
     */
    @Override
    public void setTimetables(Timetable[] timetables) {
        this.timetables = timetables;
    }

    /**
     * @param retrieveInteractor the RetrieveTimetableInputBoundary used to create a view model of
     *                           an updated timetable.
     */
    @Override
    public void setRetrieveInteractor(RetrieveTimetableInputBoundary retrieveInteractor) {
        this.retrieveInteractor = retrieveInteractor;
    }
}
