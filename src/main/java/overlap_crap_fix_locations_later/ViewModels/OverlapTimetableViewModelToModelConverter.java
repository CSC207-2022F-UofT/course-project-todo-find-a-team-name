package overlap_crap_fix_locations_later.ViewModels;

import retrieve_timetable_use_case.application_business.BlockModel;
import retrieve_timetable_use_case.application_business.CourseModel;
import retrieve_timetable_use_case.application_business.SectionModel;
import retrieve_timetable_use_case.application_business.TimetableModel;

import java.util.ArrayList;

public class OverlapTimetableViewModelToModelConverter {
    /**
     * Convert an OverlapTimetableViewModel to a TimetableModel.
     **/
    public static TimetableModel convertOverlapTimetableViewModelToModel(OverlapTimetableViewModel timetableVM) {
        ArrayList<CourseModel> courseModelList = new ArrayList<>();

        for (OverlapTimetableCourseViewModel courseViewModel : timetableVM.getCourses()) {
            courseModelList.add(convertOverlapTimetableCourseViewModelToModel(courseViewModel));
        }

        return new TimetableModel(courseModelList);
    }

    /**
     * Convert an OverlapTimetableCourseViewModel to a CourseModel.
     **/
    public static CourseModel convertOverlapTimetableCourseViewModelToModel(OverlapTimetableCourseViewModel courseVM) {
        ArrayList<SectionModel> sectionModels = new ArrayList<>();

        for (OverlapTimetableSectionViewModel sectionVM : courseVM.getSections()) {
            sectionModels.add(convertOverlapTimetableSectionViewModelToModel(sectionVM));
        }
        return new CourseModel(courseVM.getTitle(), sectionModels, courseVM.getCourseSession(),
                courseVM.getCourseCode(), courseVM.getBreadth());
    }

    /**
     * Convert an OverlapTimetableSectionViewModel to a SectionModel.
     **/
    public static SectionModel convertOverlapTimetableSectionViewModelToModel(OverlapTimetableSectionViewModel sectionVM) {
        ArrayList<BlockModel> blockModels = new ArrayList<>();
        for (OverlapTimetableBlockViewModel blockVM : sectionVM.getBlocks()) {
            blockModels.add(convertOverlapTimetableBlockViewModelToModel(blockVM));
        }

        return new SectionModel(sectionVM.getCode(), sectionVM.getInstructor(), blockModels);

    }

    /**
     * Convert an OverlapTimetableBlockViewModel to a BlockModel.
     **/
    public static BlockModel convertOverlapTimetableBlockViewModelToModel(OverlapTimetableBlockViewModel blockVM) {
        return new BlockModel(blockVM.getDay(), blockVM.getStartTime(), blockVM.getEndTime(), blockVM.getRoom());
    }

}
