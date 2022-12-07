package overlap_crap_fix_locations_later;

import display_timetable_use_case.interface_adapters.TimetableViewBlockModel;
import display_timetable_use_case.interface_adapters.TimetableViewCourseModel;
import display_timetable_use_case.interface_adapters.TimetableViewModel;
import display_timetable_use_case.interface_adapters.TimetableViewSectionModel;
import overlap_crap_fix_locations_later.InputBoundaries.OverlapMaxInputBoundary;
import overlap_crap_fix_locations_later.InputBoundaries.TimetableMatchInputBoundary;
import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Flow;

public class OverlapMaximizationController implements OverlapMaxInputBoundary {

    private final TimetableMatchInputBoundary timetableMatcher;
    private TimetableModel mainTable;
    private List<TimetableModel> timetables;

    private Flow.Publisher publisher;

    OverlapMaximizationController(TimetableMatchInputBoundary timeTableMatcher, Flow.Publisher publisher) {
        this.timetableMatcher = timeTableMatcher;
        this.publisher = publisher;
        this.mainTable = null;
        this.timetables = null;

        publisher.subscribe(this);
    }

    public static TimetableModel convertTimetableViewModelToModel(TimetableViewModel table) {
        ArrayList<CourseModel> courseModelList = new ArrayList<>();

        for (TimetableViewCourseModel course : table.getCourseData()) {
            CourseModel courseModel = course.getSectionModels();
            courseModelList.add(courseModel);
        }
        return new TimetableModel(courseModelList);
    }

    public static CourseModel convertTimetableCourseViewModelToModel(TimetableViewCourseModel model) {
        ArrayList<SectionModel> sectionModels = new ArrayList<>();

        for (TimetableViewSectionModel sectionModel : model.getSectionModels()) {

            courseModelList.add(sectionModel);
        }
        return new TimetableModel(courseModelList);

    }

    public static SectionModel convertTimetableSectionViewModelToModel(TimetableViewSectionModel sectionModel) {
        ArrayList<BlockModel> blockModels = new ArrayList<>();
        for (TimetableViewBlockModel blockModel : sectionModel.getBlockModels()) {
            blockModels.add(convertTimetableBlockViewModelToModel(blockModel));
        }

        return new SectionModel(sectionModel.getCode(), sectionModel, )

    }

    public static BlockModel convertTimetableBlockViewModelToModel(TimetableViewBlockModel blockModel) {
        return new BlockModel(blockModel.getDay(), blockModel.getStartTime(), blockModel.getEndTime(),
                blockModel.getRoom());
    }


    // TODO: For the moment, this is a string for testing. Change it later.
    public String selectedTimetable;

    /**
     * Set up to receive data from a subscription. Note that we expect 1 data bundle per InputDialog.
     **/
    @Override
    public void onSubscribe(Flow.Subscription subscription) {
        subscription.request(1);

    }

    @Override
    public void onNext(HashMap<OverlapInputDialogDataKeys, Object> items) {
        this.mainTable = (TimetableModel) items.get(OverlapInputDialogDataKeys.mainTable);
        this.timetables = (ArrayList<TimetableModel>) items.get(OverlapInputDialogDataKeys.candidateTimetables);
    }

    @Override
    public void onError(Throwable throwable) {
        System.out.println("Hey, we got an error from the OverlapMaximization's associated publisher. Check it out.");
    }

    @Override
    public void onComplete() {
        System.out.println("Completion called!");
    }

}
