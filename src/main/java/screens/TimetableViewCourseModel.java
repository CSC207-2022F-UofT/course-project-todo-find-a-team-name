package screens;

import java.util.List;

public class TimetableViewCourseModel {
    private final String code;
    private final List<TimetableViewSectionModel> sectionModels;

    public TimetableViewCourseModel(String code, List<TimetableViewSectionModel> sectionModels) {
        this.code = code;
        this.sectionModels = sectionModels;
    }

    public String getCode() {
        return code;
    }

    public List<TimetableViewSectionModel> getSectionModels() {
        return sectionModels;
    }
}
