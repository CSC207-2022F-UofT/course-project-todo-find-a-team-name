package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TimetableCourseTest {

    @Test
    void defaultConstructorSucceeds(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
        Section s2 = new Section("TUT0102", "inst2", new ArrayList<>());

        try{
            new TimetableCourse("Test Course",
                    new ArrayList<>(List.of(s1, s2)),
                    "F", "EGX101", "BR1");
        }
        catch (InvalidSectionsException e){
            fail("Constructor should not have thrown InvalidSectionsException.");
        }
    }

    @Test
    void defaultConstructorFails(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
        Section s2 = new Section("LEC0102", "inst2", new ArrayList<>());
        try{
            new TimetableCourse("Test Course",
                    new ArrayList<>(List.of(s1, s2)),
                    "F", "EGX101", "BR1");
            fail("Constructor should have thrown InvalidSectionsException.");
        } catch (InvalidSectionsException e){
            assertTrue(true);
        }
    }

    @Test
    void setPracticalEmpty(){
        try{
            TimetableCourse c = new TimetableCourse("Test Course",
                    new ArrayList<>(), "", "", "");
            Section s1 = new Section("PRA0101", "inst1", new ArrayList<>());
            c.setPractical(s1);
            assertEquals(c.getPractical(), s1);
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void setPracticalOverride(){
        Section s1 = new Section("PRA0101", "inst1", new ArrayList<>());
        Section s2 = new Section("PRA0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setPractical(s2);
            assertEquals(c.getPractical(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void setLectureEmpty(){
        try{
            TimetableCourse c = new TimetableCourse("Test Course",
                    new ArrayList<>(), "", "", "");
            Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
            c.setLecture(s1);
            assertEquals(c.getLecture(), s1);
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void setLectureOverride(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
        Section s2 = new Section("LEC0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setLecture(s2);
            assertEquals(c.getLecture(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void setTutorialEmpty(){
        try{
            TimetableCourse c = new TimetableCourse("Test Course",
                    new ArrayList<>(), "", "", "");
            Section s1 = new Section("TUT0101", "inst1", new ArrayList<>());
            c.setTutorial(s1);
            assertEquals(c.getTutorial(), s1);
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void setTutorialOverride(){
        Section s1 = new Section("TUT0101", "inst1", new ArrayList<>());
        Section s2 = new Section("TUT0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setTutorial(s2);
            assertEquals(c.getTutorial(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }
    }

    @Test
    void SetSectionPractical(){
        Section s1 = new Section("PRA0101", "inst1", new ArrayList<>());
        Section s2 = new Section("PRA0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setSection(s2);
            assertEquals(c.getPractical(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }

    }

    @Test
    void SetSectionLecture(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<>());
        Section s2 = new Section("LEC0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setSection(s2);
            assertEquals(c.getLecture(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }

    }

    @Test
    void SetSectionTutorial(){
        Section s1 = new Section("TUT0101", "inst1", new ArrayList<>());
        Section s2 = new Section("TUT0102", "inst2", new ArrayList<>());

        try{
            ArrayList<Section> sections = new ArrayList<>();
            sections.add(s1);
            TimetableCourse c = new TimetableCourse("Test Course", sections, "", "", "");
            c.setSection(s2);
            assertEquals(c.getTutorial(), s2);
            assertFalse(c.getSections().contains(s1));
        }
        catch (InvalidSectionsException e){
            fail("This should not throw an exception.");
        }

    }
}