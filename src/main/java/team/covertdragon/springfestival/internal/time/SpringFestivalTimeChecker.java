package team.covertdragon.springfestival.internal.time;

import team.covertdragon.springfestival.SpringFestivalConfig;

import java.util.ArrayList;
import java.util.List;

public final class SpringFestivalTimeChecker {

    public static final SpringFestivalTimeChecker INSTANCE = new SpringFestivalTimeChecker();

    private final List<SpringFestivalTimeProvider> checkers = new ArrayList<>();

    private boolean isDuringSpringFestival = false, hasQueriedTime = false;

    private SpringFestivalTimeChecker() {
        this.checkers.add(SpringFestivalTimeProviderLocal.INSTANCE);
        this.checkers.add(SpringFestivalTimeProvider.fromURL("https://covertdragon.team/springfestival/date", "SpringFestival-DateQuerying"));
        if (SpringFestivalConfig.useFuzzySpringFestivalMatcher) {
            this.checkers.add(SpringFestivalTimeProviderFuzzyMatch.INSTANCE);
        }
    }

    public final boolean isDuringSpringFestivalSeason() {
        if (hasQueriedTime) {
            return isDuringSpringFestival;
        }
        for (SpringFestivalTimeProvider checker : this.checkers) {
            this.isDuringSpringFestival |= checker.isDuringSpringFestival();
            if (isDuringSpringFestival) {
                break;
            }
        }
        this.hasQueriedTime = true;
        return isDuringSpringFestival;
    }

    public final void reset() {
        this.isDuringSpringFestival = false;
        this.hasQueriedTime = false;
    }
}
