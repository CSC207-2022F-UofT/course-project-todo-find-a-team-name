package overlap_crap_fix_locations_later;

import org.junit.Test;
import retrieve_timetable_use_case.BlockModel;
import retrieve_timetable_use_case.SectionModel;

import java.util.List;

import static org.junit.Assert.assertThrows;

public class TestCalculateSectionHoursInteractor {

    private CalculateSectionHoursInteractor calculateSectionHoursInteractor = new CalculateSectionHoursInteractor();

    /** Test that calculateHoursOfSection works for a basic case. **/
    @Test
    public void test_calculateHoursOfSection_basic(){
        BlockModel testBlock = new BlockModel(24, 10.00, 12.00, "BA137");
        SectionModel testSection = new SectionModel("CSC110", "Mario-chan", List.of(testBlock));
        Double actual = calculateSectionHoursInteractor.calculateHoursOfSection(testSection);
        Double expected = 2.0;
        assert actual.compareTo(expected) == 0;
    }

    /** Test that calculateHoursOfSection works for a zero case. **/
    @Test
    public void test_calculateHoursOfSection_zero(){
        BlockModel testBlock = new BlockModel(24, 10.00, 10.00, "BA137");
        SectionModel testSection = new SectionModel("CSC110", "Mario-chan", List.of(testBlock));
        Double actual = calculateSectionHoursInteractor.calculateHoursOfSection(testSection);
        Double expected = 0.0;
        assert actual.compareTo(expected) == 0;
    }

    /** Test that calculateHoursOfSection correctly throws an exception if it somehow produces an output
     * that is over 24 hours.
     */
    @Test
    public void test_calculateHoursOfSection_terrible_arguments_too_huge(){
        BlockModel testBlock = new BlockModel(24, 00.00, 137.00, "BA137");
        SectionModel testSection = new SectionModel("CSC110", "Mario-chan", List.of(testBlock));
        assertThrows(IllegalArgumentException.class, () -> {calculateSectionHoursInteractor.calculateHoursOfSection(testSection);});
    }

    /** Test that calculateHoursOfSection correctly throws an exception if it somehow produces an output
     * that is negative hours.
     */
    @Test
    public void test_calculateHoursOfSection_terrible_arguments_negative_hours(){
        BlockModel testBlock = new BlockModel(24, 100.00, 0.00, "BA137");
        SectionModel testSection = new SectionModel("CSC110", "Mario-chan", List.of(testBlock));
        assertThrows(IllegalArgumentException.class, () -> {calculateSectionHoursInteractor.calculateHoursOfSection(testSection);});
    }
}
