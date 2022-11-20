package screens;

import java.util.List;

public class TimetableViewSectionModel {
    private final String code;
    private final List<TimetableViewBlockModel> blockModels;

    public TimetableViewSectionModel(String code, List<TimetableViewBlockModel> blockModels) {
        this.code = code;
        this.blockModels = blockModels;
    }

    public String getCode() {
        return code;
    }

    public List<TimetableViewBlockModel> getBlockModels() {
        return blockModels;
    }
}
