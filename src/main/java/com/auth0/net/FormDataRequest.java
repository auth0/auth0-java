package com.auth0.net;

import java.io.File;

/**
 * A request class that encodes its content as a form.
 * i.e. for uploading attachments.
 *
 * @param <T> The type expected to be received as part of the response.
 */
@SuppressWarnings("UnusedReturnValue")
interface FormDataRequest<T> extends Request<T> {

    /**
     * Adds a key-value part to the form of this request
     *
     * @param name  the name of the part
     * @param value the value of the part
     * @return this same request instance
     */
    FormDataRequest<T> addPart(String name, String value);

    /**
     * Adds a file part to the form of this request
     *
     * @param name      the name of the part
     * @param file      the file contents to send in this part
     * @param mediaType the file contents media type
     * @return this same request instance
     */
    FormDataRequest<T> addPart(String name, File file, String mediaType);
}
