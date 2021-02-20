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

package org.scijava.plugin;

import org.scijava.Priority;
import org.scijava.UIDetails;

/**
 * Top-level interface for plugins. Plugins discoverable at runtime must
 * implement this interface and be annotated with @{@link Plugin}.
 * <p>
 * What all plugins have in common is that they are declared using an annotation
 * (@{@link Plugin}), and discovered if present on the classpath at runtime.
 * </p>
 * <p>
 * The core types of plugins are as follows:
 * </p>
 * <ul>
 * <li>{@link org.scijava.app.App} - metadata about a SciJava application.</li>
 * <li>{@link org.scijava.command.Command} - plugins that are executable. These
 * plugins typically perform a discrete operation, and are accessible via the
 * application menus.</li>
 * <li>{@link org.scijava.console.ConsoleArgument} - plugins that handle
 * arguments passed to the application as command line parameters.</li>
 * <li>{@link org.scijava.convert.Converter} - plugins which translate objects
 * between data types.</li>
 * <li>{@link org.scijava.display.Display} - plugins that visualize objects,
 * often used to display module outputs.</li>
 * <li>{@link org.scijava.io.IOPlugin} - plugins that read or write data.</li>
 * <li>{@link org.scijava.module.process.PreprocessorPlugin} - plugins that
 * perform preprocessing on modules. A preprocessor plugin is a discoverable
 * {@link org.scijava.module.process.ModulePreprocessor}.</li>
 * <li>{@link org.scijava.module.process.PostprocessorPlugin} - plugins that
 * perform postprocessing on modules. A
 * {@link org.scijava.module.process.PostprocessorPlugin} is a discoverable
 * {@link org.scijava.module.process.ModulePostprocessor}.</li>
 * <li>{@link org.scijava.platform.Platform} - plugins for defining
 * platform-specific behavior.</li>
 * <li>{@link org.scijava.script.ScriptLanguage} - plugins that enable executing
 * scripts in particular languages as SciJava modules.</li>
 * <li>{@link org.scijava.service.Service} - plugins that define new API in a
 * particular area.</li>
 * <li>{@link org.scijava.tool.Tool} - plugins that map user input (e.g.,
 * keyboard and mouse actions) to behavior. They are usually rendered as icons
 * in the application toolbar.</li>
 * <li>{@link org.scijava.widget.InputWidget} - plugins that render UI widgets
 * for the {@link org.scijava.widget.InputHarvester} preprocessor.</li>
 * </ul>
 *
 * @author Curtis Rueden
 * @see Plugin
 * @see PluginService
 */
public interface SciJavaPlugin {
    // top-level marker interface for discovery via annotation indexes

    /**
     * The type of plugin; e.g., {@link org.scijava.service.Service}.
     */
    Class<? extends SciJavaPlugin> type();

    /**
     * The name of the plugin.
     */
    default String name() {
        return "";
    }

    /**
     * The human-readable label to use (e.g., in the menu structure).
     */
    default String label() {
        return "";
    }

    /**
     * A longer description of the plugin (e.g., for use as a tool tip).
     */
    default String description() {
        return "";
    }

    /**
     * Abbreviated menu path defining where the plugin is shown in the menu
     * structure. Uses greater than signs ({@code >}) as a separator; e.g.: "Image
     * &gt; Overlay &gt; Properties..." defines a "Properties..." menu item within
     * the "Overlay" submenu of the "Image" menu. Use either {@link #menuPath} or
     * {@link #menu_} but not both.
     */
    default String menuPath() {
        return "";
    }

    /**
     * Full menu path defining where the plugin is shown in the menu structure.
     * This construction allows menus to be fully specified including mnemonics,
     * accelerators and icons. Use either {@link #menuPath} or {@link #menu_} but
     * not both.
     */
    default Menu[] menu() {
        return new Menu[]{};
    }

    /**
     * String identifier naming the menu to which this plugin belongs, or in the
     * case of a tool, the context menu that should be displayed while the tool is
     * active. The default value of {@link UIDetails#APPLICATION_MENU_ROOT}
     * references the menu structure of the primary application window.
     */
    default String menuRoot() {
        return UIDetails.APPLICATION_MENU_ROOT;
    }

    /**
     * Path to the plugin's icon (e.g., shown in the menu structure).
     */
    default String iconPath() {
        return "";
    }

    /**
     * The plugin index returns plugins sorted by priority. For example, this is
     * useful for {@link org.scijava.service.Service}s to control which service
     * implementation is chosen when multiple implementations are present in the
     * classpath, as well as to force instantiation of one service over another
     * when the dependency hierarchy does not dictate otherwise.
     * <p>
     * Any double value is allowed, but for convenience, there are some presets:
     * </p>
     * <ul>
     * <li>{@link Priority#FIRST}</li>
     * <li>{@link Priority#VERY_HIGH}</li>
     * <li>{@link Priority#HIGH}</li>
     * <li>{@link Priority#NORMAL}</li>
     * <li>{@link Priority#LOW}</li>
     * <li>{@link Priority#VERY_LOW}</li>
     * <li>{@link Priority#LAST}</li>
     * </ul>
     *
     * @see org.scijava.service.Service
     */
    default double priority() {
        return Priority.NORMAL;
    }

    /**
     * Whether the plugin can be selected in the user interface. A plugin's
     * selection state (if any) is typically rendered in the menu structure using
     * a checkbox or radio button menu item (see {@link #selectionGroup}).
     */
    default boolean selectable() {
        return false;
    }

    /**
     * For selectable plugins, specifies a name defining a group of linked
     * plugins, only one of which is selected at any given time. Typically this is
     * rendered in the menu structure as a group of radio button menu items. If no
     * group is given, the plugin is assumed to be a standalone toggle, and
     * typically rendered as a checkbox menu item.
     */
    default String selectionGroup() {
        return "";
    }

    /**
     * When false, the plugin is grayed out in the user interface, if applicable.
     */
    default boolean enabled() {
        return true;
    }

    /**
     * When false, the plugin is not displayed in the user interface.
     */
    default boolean visible() {
        return true;
    }

    /**
     * Provides a "hint" as to whether the plugin would behave correctly in a
     * headless context.
     * <p>
     * Plugin developers should not specify {@code headless = true} unless the
     * plugin refrains from using any UI-specific features (e.g., AWT or Swing
     * calls).
     * </p>
     * <p>
     * Of course, merely setting this flag does not guarantee that the plugin will
     * not invoke any headless-incompatible functionality, but it provides an
     * extra safety net for downstream headless code that wishes to be
     * conservative in which plugins it allows to execute.
     * </p>
     */
    default boolean headless() {
        return false;
    }

    /**
     * Defines a function that is called to initialize the plugin in some way.
     */
    default Runnable initializer() {
        return () -> {
        };
    }

    /**
     * A list of additional attributes which can be used to extend this annotation
     * beyond its built-in capabilities.
     * <p>
     * Note to developers: when designing new plugin types, it is tempting to use
     * this attribute to store additional information about each plugin. However,
     * we suggest doing so only if you need that additional information before
     * creating an instance of the plugin: e.g., to decide whether to instantiate
     * one, or even whether to load the annotated plugin class at all. If you are
     * going to create a plugin instance anyway, it is cleaner and more type-safe
     * to add proper API methods to the plugin type's interface reporting the same
     * information.
     * </p>
     */
    default Attr[] attrs() {
        return new Attr[]{};
    }
}
