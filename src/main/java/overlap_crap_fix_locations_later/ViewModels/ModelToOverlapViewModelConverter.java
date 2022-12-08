package overlap_crap_fix_locations_later.ViewModels;

import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;

public class ModelToOverlapViewModelConverter {
    /**
     * Convenience method to turn a BlockModel into an OverlapTimetableBlockViewModel.
     *
     * @param blockModel
     */
    public static OverlapTimetableBlockViewModel convertBlockModel(BlockModel blockModel) {
        return new OverlapTimetableBlockViewModel(blockModel.getDay(), blockModel.getStartTime(),
                blockModel.getEndTime(), blockModel.getRoom());
    }

    /**
     * Convenience constructor for if you want to quickly make this out of a sectionModel.
     *
     * @param sectionModel - the original sectionModel you want to turn into a ViewModel.
     */
    public static OverlapTimetableSectionViewModel convertSectionModel(SectionModel sectionModel) {
        ArrayList<OverlapTimetableBlockViewModel> blockViewModels = new ArrayList<>();

        for (BlockModel blockModel : sectionModel.getBlocks()) {
            blockViewModels.add(ModelToOverlapViewModelConverter.convertBlockModel(blockModel));
        }

        return new OverlapTimetableSectionViewModel(sectionModel.getCode(), sectionModel.getInstructor(),
                blockViewModels);
    }

    /**
     * Convenience constructor to quickly make this ViewModel out of a CourseModel
     **/
    public static OverlapTimetableCourseViewModel convertCourseModel(CourseModel courseModel) {

        ArrayList<OverlapTimetableSectionViewModel> sectionViewModels = new ArrayList<>();
        for (SectionModel sectionModel : courseModel.getSections()) {
            sectionViewModels.add(ModelToOverlapViewModelConverter
                    .convertSectionModel(sectionModel));
        }

        return new OverlapTimetableCourseViewModel(courseModel.getTitle(), sectionViewModels,
                courseModel.getCourseSession(), courseModel.getCourseCode(), courseModel.getBreadth());
    }

    public static OverlapTimetableViewModel convertTimetableModel(TimetableModel timetableModel) {
        ArrayList<OverlapTimetableCourseViewModel> courseViewModels = new ArrayList<>();
        for (CourseModel courseModel : timetableModel.getCourses()) {
            courseViewModels.add(ModelToOverlapViewModelConverter.convertCourseModel(courseModel));
        }
        return new OverlapTimetableViewModel(courseViewModels);
    }
}
