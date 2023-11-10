package io.github.itzispyder.dotchaos;

import io.github.itzispyder.dotchaos.gui.Window;
import io.github.itzispyder.dotchaos.util.Randomizer;
import io.github.itzispyder.dotchaos.util.Scheduler;

public interface Global {

    Window window = new Window("ItziSpyder's Lab");
    Randomizer random = new Randomizer();
    Scheduler scheduler = new Scheduler();
}
