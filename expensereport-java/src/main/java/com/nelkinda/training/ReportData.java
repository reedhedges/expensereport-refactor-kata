package com.nelkinda.training;

import java.util.List;

public class ReportData {
    private final List<ReportLineData> lines;
    private final int mealExpenses;
    private final int total;

    ReportData(List<ReportLineData> lines, int mealExpenses, int total) {
        this.lines = lines;
        this.mealExpenses = mealExpenses;
        this.total = total;
    }

    public List<ReportLineData> getLines() {
        return lines;
    }

    public int getMealExpenses() {
        return mealExpenses;
    }

    public int getTotal() {
        return total;
    }
}
