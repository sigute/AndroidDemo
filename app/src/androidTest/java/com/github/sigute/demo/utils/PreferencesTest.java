package com.github.sigute.demo.utils;

import android.support.test.InstrumentationRegistry;

import junit.framework.Assert;

import org.junit.Test;

public class PreferencesTest {
    @Test
    public void testPreferences() {
        Preferences preferences = new Preferences(InstrumentationRegistry.getContext());
        Assert.assertTrue(preferences.getLastRefreshedDate() == null);
        preferences.setRefreshed();
        Assert.assertTrue(preferences.getLastRefreshedDate() != null);
    }
}
