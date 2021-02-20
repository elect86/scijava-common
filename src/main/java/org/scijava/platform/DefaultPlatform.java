/*
 * #%L
 * SciJava Common shared library for SciJava software.
 * %%
 * Copyright (C) 2009 - 2020 SciJava developers.
 * %%
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice,
 *    this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 * #L%
 */

package org.scijava.platform;

import java.io.IOException;
import java.net.URL;

import org.scijava.Priority;
import org.scijava.plugin.Plugin;

/**
 * A platform implementation for default handling of platform issues.
 * 
 * @author Curtis Rueden
 * @author Johannes Schindelin
 */
public class DefaultPlatform extends AbstractPlatform {

	@Override
	public String name() {
		return "Default";
	}

	@Override
	public double priority() {
		return Priority.VERY_LOW;
	}

	// -- PlatformHandler methods --

	/**
	 * Falls back to calling known browsers.
	 * <p>
	 * Based on <a
	 * href="http://www.centerkey.com/java/browser/">BareBonesBrowserLaunch</a>.
	 * </p>
	 * <p>
	 * The utility 'xdg-open' launches the URL in the user's preferred browser,
	 * therefore we try to use it first, before trying to discover other browsers.
	 * </p>
	 */
	@Override
	public void open(final URL url) throws IOException {
		final String[] browsers =
			{ "xdg-open", "netscape", "firefox", "konqueror", "mozilla", "opera",
				"epiphany", "lynx" };
		for (final String browser : browsers) {
			try {
				final int exitCode = getPlatformService().exec(browser, url.toString());
				if (exitCode == 0) return;
			}
			catch (final IOException e) {
				// browser executable was invalid; try the next one
			}
		}
		throw new IOException("Could not open " + url);
	}

}
