/**
 *  Copyright 2009-2020 PrimeTek.
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  Licensed under PrimeFaces Commercial License, Version 1.0 (the "License");
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package org.primefaces.california.view;

import java.io.Serializable;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;

@Named
@SessionScoped
public class GuestPreferences implements Serializable {

    private String layout = "moody";

    private String theme = "noir";

    private boolean darkMenu = false;

    private boolean gradientMenu = false;

    private boolean darkMegaMenu = true;

    private boolean gradientMegaMenu = false;

    private String menuLayout = "static";

    private String profileMode = "inline";

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getLayout() {
        return layout;
    }

    public void setLayout(String layout) {
        this.layout = layout;
    }

    public boolean isDarkMenu() {
        return this.darkMenu;
    }

    public boolean isGradientMenu() {
        return this.gradientMenu;
    }

    public boolean isDarkMegaMenu() {
        return this.darkMegaMenu;
    }

    public boolean isGradientMegaMenu() {
        return this.gradientMegaMenu;
    }

    public void setMenuMode(boolean dark, boolean gradient, String theme) {
        this.darkMenu = dark;
        this.gradientMenu = gradient;
        this.theme = theme;
    }

    public void setMegaMenuMode(boolean dark, boolean gradient) {
        this.darkMegaMenu = dark;
        this.gradientMegaMenu = gradient;
    }

    public String getMenuLayout() {
        return menuLayout;
    }

    public void setMenuLayout(String menuLayout) {
        this.menuLayout = menuLayout;

        if (this.menuLayout.equals("layout-wrapper-horizontal-sidebar")) {
            this.profileMode = "topbar";
        }
    }

    public String getProfileMode() {
        return profileMode;
    }

    public void setProfileMode(String profileMode) {
        if (this.menuLayout.equals("layout-wrapper-horizontal-sidebar")) {
            this.profileMode = "topbar";
        }
        else {
            this.profileMode = profileMode;
        }
    }
}
