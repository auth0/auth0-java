package com.auth0.net;

import java.io.File;

interface FormDataRequest<T> extends Request<T> {

    FormDataRequest<T> addPart(String name, String value);

    FormDataRequest<T> addPart(String name, File file, String dataType);
}
