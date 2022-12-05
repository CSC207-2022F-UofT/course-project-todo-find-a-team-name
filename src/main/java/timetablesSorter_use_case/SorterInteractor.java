package timetablesSorter_use_case;

import display_timetable_use_case.interface_adapters.TimetableViewModel;

/**
 * The interactor used to sort timetables by User preferences.
 * Instance Attributes:
 * timetables - the timetables being sorted through.
 * presenter - the presenter attached to the use case.
 */
public class SorterInteractor implements SorterInputBoundary{
    private final TimetableViewModel[] timetables;
    private SorterOutputBoundary presenter;
    public SorterInteractor(TimetableViewModel[] timetables, SorterOutputBoundary presenter) {
        this.timetables = timetables;
        this.presenter = presenter;
    }

    /**
     * @param requestModel data structure required for sorting
     * @return returns a SorterResponseModel which contains an array of timetableViewModels to present in order in
     * AllTimetablesScreen
     * TODO: implement preferenceSort and getScore and figure out a different way that using viewmodel here for clean
     * since interactor
     */
    @Override
    public SorterResponseModel preferenceSort(SorterRequestModel requestModel) {
        System.out.println(requestModel.getBreakButton());
        String b = "breaks (try to have at least an hour of break between classes)";
        String c = "commuter (shove everything into as few days as possible)";
        String d = "Don't care";
        for (int i = 0; i < timetables.length; i++) {
            int score = getScore(timetables[i]);
        }
//        remove below
        return new SorterResponseModel(timetables);
    }

    private int getScore(TimetableViewModel timetable) {
        return 0;
    }

}
