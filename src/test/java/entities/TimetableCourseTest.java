package entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class TimetableCourseTest {

    @Test
    void defaultConstructorSucceeds(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<Block>());
        Section s2 = new Section("TUT0102", "inst2", new ArrayList<Block>());

        try{
            TimetableCourse c = new TimetableCourse("Test Course",
                    new ArrayList<Section>(List.of(s1, s2)),
                    "F", "EGX101", "BR1");
        }
        catch (InvalidSectionsException e){
            fail("Constructor should not have thrown InvalidSectionsException.");
        }
    }

    @Test
    void defaultConstructorFails(){
        Section s1 = new Section("LEC0101", "inst1", new ArrayList<Block>());
        Section s2 = new Section("LEC0102", "inst2", new ArrayList<Block>());
        boolean expectedExceptionThrown = false;

        try{
            TimetableCourse c = new TimetableCourse("Test Course",
                    new ArrayList<Section>(List.of(s1, s2)),
                    "F", "EGX101", "BR1");
            fail("Constructor should have thrown InvalidSectionsException.");
        } catch (InvalidSectionsException e){
            expectedExceptionThrown = true;
        }
    }

}