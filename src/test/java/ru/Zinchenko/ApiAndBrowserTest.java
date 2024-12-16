package ru.Zinchenko;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;


@SelectPackages({"ru.Zinchenko.tests.APITests", "ru.Zinchenko.tests.browserTests"})
@Suite
public class ApiAndBrowserTest {
}
