/*
 * Copyright (c) 2013, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.github.fge.jsonschema.library;

import com.github.fge.jsonschema.util.Frozen;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/*
 * Wrapper class over a Map<String, T>
 *
 * Use it to collect what exists as strings, entries etc.
 */
public final class Dictionary<T>
    implements Frozen<MutableDictionary<T>>
{
    final Map<String, T> entries;

    Dictionary(final Map<String, T> map)
    {
        entries = ImmutableMap.copyOf(map);
    }

    Dictionary(final MutableDictionary<T> builder)
    {
        this(builder.entries);
    }

    public boolean hasEntry(final String key)
    {
        return entries.containsKey(key);
    }

    public Set<String> missingEntriesFrom(final Set<String> set)
    {
        final Set<String> ret = Sets.newTreeSet();
        ret.addAll(set);
        ret.removeAll(entries.keySet());
        return ret;
    }

    public Collection<T> valuesForKeys(final Set<String> keys)
    {
        final Map<String, T> map = Maps.newTreeMap();
        map.putAll(entries);
        map.keySet().retainAll(keys);
        return map.values();
    }

    @Override
    public MutableDictionary<T> thaw()
    {
        return new MutableDictionary<T>(this);
    }
}
