package com.nelkinda.training;

import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.GenericDiffReporter;

public class IdeaReporter extends GenericDiffReporter {
    public IdeaReporter() {
        super(new DiffInfo("/home/gregor/apps/idea/bin/idea.sh", "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS));
    }
}
