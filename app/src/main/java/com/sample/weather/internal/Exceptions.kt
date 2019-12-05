package com.sample.weather.internal

import java.io.IOException
import java.lang.Exception

class ConnectivityException: IOException()

class LocationPermissionNotGrantedException: Exception()