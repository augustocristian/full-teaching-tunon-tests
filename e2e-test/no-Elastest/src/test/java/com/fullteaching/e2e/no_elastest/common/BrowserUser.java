/*
 * (C) Copyright 2017 OpenVidu (http://openvidu.io/)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.fullteaching.e2e.no_elastest.common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

public class BrowserUser {

    final static Logger log = getLogger(lookup().lookupClass());
    protected WebDriver driver;
    protected WebDriverWait waiter;
    protected String clientData;
    protected int timeOfWaitInSeconds;

    public BrowserUser(String clientData, int timeOfWaitInSeconds) {
        this.clientData = clientData;
        this.timeOfWaitInSeconds = timeOfWaitInSeconds;
    }

    public WebDriver getDriver() {
        return this.driver;
    }

    public void setDriver(WebDriver dr) {
        this.driver = dr;
    }

    public WebDriverWait getWaiter() {
        return this.waiter;
    }

    public void waitUntil(ExpectedCondition<?> condition, String errorMessage) {
        try {
            this.waiter.until(condition);
        } catch (org.openqa.selenium.TimeoutException timeout) {
            log.error(errorMessage);
            throw new org.openqa.selenium.TimeoutException("\"" + errorMessage + "\" (checked with condition) > " + timeout.getMessage());
        }
    }

    public String getClientData() {
        return this.clientData;
    }

    public int getTimeOfWait() {
        return this.timeOfWaitInSeconds;
    }

    protected void newWaiter(int timeOfWait) {
        this.waiter = new WebDriverWait(this.driver, timeOfWait);
    }

    protected void configureDriver() {
        this.waiter = new WebDriverWait(this.driver, this.timeOfWaitInSeconds);
    }

    public void dispose() {
        this.driver.quit();
    }

    public Object runJavascript(String script, Object... args) {
        return ((JavascriptExecutor) this.driver).executeScript(script, args);
    }

}