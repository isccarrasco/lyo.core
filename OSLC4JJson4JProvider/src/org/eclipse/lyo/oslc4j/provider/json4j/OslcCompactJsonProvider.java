/*******************************************************************************
 * Copyright (c) 2012 IBM Corporation.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * and Eclipse Distribution License v. 1.0 which accompanies this distribution.
 *  
 * The Eclipse Public License is available at http://www.eclipse.org/legal/epl-v10.html
 * and the Eclipse Distribution License is available at
 * http://www.eclipse.org/org/documents/edl-v10.php.
 *
 * Contributors:
 *
 *     Russell Boykin       - initial API and implementation
 *     Alberto Giammaria    - initial API and implementation
 *     Chris Peters         - initial API and implementation
 *     Gianluca Bernardini  - initial API and implementation
 *******************************************************************************/
package org.eclipse.lyo.oslc4j.provider.json4j;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.ext.MessageBodyReader;
import javax.ws.rs.ext.MessageBodyWriter;
import javax.ws.rs.ext.Provider;

import org.eclipse.lyo.oslc4j.core.model.Compact;
import org.eclipse.lyo.oslc4j.core.model.OslcMediaType;

@Provider
@Produces(OslcMediaType.APPLICATION_X_OSLC_COMPACT_JSON)
@Consumes(OslcMediaType.APPLICATION_X_OSLC_COMPACT_JSON)
public final class OslcCompactJsonProvider
       extends AbstractOslcRdfJsonProvider
       implements MessageBodyReader<Compact>,
                  MessageBodyWriter<Compact>
{
    public OslcCompactJsonProvider()
    {
        super();
    }

    @Override
    public long getSize(final Compact      compact,
                        final Class<?>     type,
                        final Type         genericType,
                        final Annotation[] annotation,
                        final MediaType    mediaType)
    {
        return -1;
    }

    @Override
    public boolean isWriteable(final Class<?>     type,
                               final Type         genericType,
                               final Annotation[] annotations,
                               final MediaType    mediaType)
    {
        return (Compact.class.isAssignableFrom(type)) &&
               (isWriteable(type,
                            annotations,
                            OslcMediaType.APPLICATION_X_OSLC_COMPACT_JSON_TYPE,
                            mediaType));
    }

    @Override
    public void writeTo(final Compact                        compact,
                        final Class<?>                       type,
                        final Type                           genericType,
                        final Annotation[]                   annotations,
                        final MediaType                      mediaType,
                        final MultivaluedMap<String, Object> map,
                        final OutputStream                   outputStream)
           throws IOException,
                  WebApplicationException
    {
        writeTo(false,
                new Compact[] {compact},
                OslcMediaType.APPLICATION_JSON_TYPE,
                outputStream);
    }

    @Override
    public boolean isReadable(final Class<?>     type,
                              final Type         genericType,
                              final Annotation[] annotations,
                              final MediaType    mediaType)
    {
        return (Compact.class.isAssignableFrom(type)) &&
               (isReadable(type,
                           OslcMediaType.APPLICATION_X_OSLC_COMPACT_JSON_TYPE,
                           mediaType));
    }

    @Override
    public Compact readFrom(final Class<Compact>                 type,
                            final Type                           genericType,
                            final Annotation[]                   annotations,
                            final MediaType                      mediaType,
                            final MultivaluedMap<String, String> map,
                            final InputStream                    inputStream)
           throws IOException,
                  WebApplicationException
    {
        final Object[] objects = readFrom(type,
                                          OslcMediaType.APPLICATION_JSON_TYPE,
                                          inputStream);

        if ((objects != null) &&
            (objects.length > 0))
        {
            final Object object = objects[0];

            if (object instanceof Compact)
            {
                return (Compact) object;
            }
        }

        return null;
    }
}