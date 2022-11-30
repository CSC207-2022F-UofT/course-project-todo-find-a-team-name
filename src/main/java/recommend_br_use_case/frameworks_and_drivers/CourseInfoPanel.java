package recommend_br_use_case.frameworks_and_drivers;

import javax.swing.*;

/**
 * Class used to show information of the course to the user.
 */
public class CourseInfoPanel extends JPanel {

    private final JLabel courseInfo;

    /**
     * Constructs courseInfoPanel with empty JLabel assigned to courseInfo.
     */
    public CourseInfoPanel(){
        super();
        courseInfo = new JLabel();
        add(courseInfo);
        setVisible(true);
    }

    /**
     * Displays course information, including course code, course title, breadth category, and information of
     * sections contained in the course.
     *
     * @param courseViewModel object containing all the information of the course shown to the user
     */
    public void showCourseInfo(RecommendBRCourseViewModel courseViewModel){
        String courseInfoHtml = courseViewModelToCourseInfoHtml(courseViewModel);
        courseInfo.setText(courseInfoHtml);
    }

    /**
     * Converts course information contained in the given RecommendBRCourseViewModel to
     * String storing course information that is suitable for displaying it as a text, using html.
     *
     * @param courseViewModel view model containing all course information
     * @return String storing course information that is suitable for displaying it as a text
     */
    private String courseViewModelToCourseInfoHtml(RecommendBRCourseViewModel courseViewModel){
        StringBuilder sb = new StringBuilder();
        sb.append("<html><p>Code: ")
                .append(courseViewModel.getCode()).append("<br>")
                .append("Title: ").append(courseViewModel.getTitle()).append("<br>")
                .append("BR: ").append(courseViewModel.getBrCategory()).append("<br>")
                .append("</p><p>Sections: </p><ul>");

        sb.append("<li>").append(courseViewModel.getLectureCode()).append("</li><ul>");
        for (String info : courseViewModel.getLectureBlockInfos()){
            sb.append("<li>").append(info).append("</li>");
        }
        sb.append("</ul>");

        if (courseViewModel.getTutorialCode() != null){
            sb.append("<li>").append(courseViewModel.getTutorialCode()).append("</li><ul>");
            for (String info : courseViewModel.getTutorialBlockInfos()){
                sb.append("<li>").append(info).append("</li>");
            }
            sb.append("</ul>");
        }
        if (courseViewModel.getPracticalCode() != null){
            sb.append("<li>").append(courseViewModel.getPracticalCode()).append("</li><ul>");
            for (String info : courseViewModel.getPracticalBlockInfos()){
                sb.append("<li>").append(info).append("</li>");
            }
            sb.append("</ul>");
        }
        sb.append("</ul></html>");
        return sb.toString();
    }


}
