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
package org.primefaces.california.view.misc;

import org.primefaces.california.domain.Theme;
import org.primefaces.california.service.ThemeService;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Named
@RequestScoped
public class ThemeSwitcherView {

    private List<Theme> themes;
    
    @Inject
    private ThemeService service;

    @PostConstruct
    public void init() {
        themes = service.getThemes();
    }
    
    public List<Theme> getThemes() {
        return themes;
    } 

    public void setService(ThemeService service) {
        this.service = service;
    }
}
