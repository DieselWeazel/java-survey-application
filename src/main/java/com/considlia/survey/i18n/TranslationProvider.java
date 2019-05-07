package com.considlia.survey.i18n;

import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.inject.Default;

import com.vaadin.flow.i18n.I18NProvider;
import com.vaadin.flow.i18n.LocaleChangeEvent;
import com.wcs.vaadin.flow.cdi.VaadinServiceEnabled;
import com.wcs.vaadin.flow.cdi.VaadinServiceScoped;

@VaadinServiceScoped
@VaadinServiceEnabled
@Default
public class TranslationProvider implements I18NProvider {

	private final List<Locale> supportedLocales = Collections.unmodifiableList(Arrays.asList(Locale.ENGLISH, Locale.GERMAN));
	private final Locale defaultLocale = Locale.ENGLISH;
	private ResourceBundle resourceBundle;
	
	@PostConstruct
	public void init() {
		init(this.defaultLocale);
	}
	
	public void onLocaleChanged(LocaleChangeEvent event) {
		init(event.getLocale());
	}
	
	public void init(Locale locale) {
		Locale languageLocale = Locale.forLanguageTag(locale.getLanguage());
		if (this.supportedLocales.contains(languageLocale)) {
			this.resourceBundle = ResourceBundle.getBundle("messages", languageLocale);
		} else {
			this.resourceBundle = ResourceBundle.getBundle("messages", this.defaultLocale);
		}
	}
	
	@Override
	public List<Locale> getProvidedLocales() {
		return supportedLocales;
	}

	@Override
	public String getTranslation(String key, Locale locale, Object... params) {
		String message = key;
		if (locale != this.resourceBundle.getLocale()) {
			this.init(locale);
		}
		try {
			message = MessageFormat.format(this.resourceBundle.getString(key), params);
		} catch (MissingResourceException e) {
			message = String.format("*** %s ***", key);
		}
		return message;
	}
}