# MovieTestApp
Simple app for technical test .
It shows a lis of popular movies, with infinite scroll, and allows the user to search movies by keyword, and the results are shown in real time.

Features:
* Dagger 2 for DI
* Retrofit & RxJava for API calls and background processing.
* MVP architecture (with UseCases with Callbacks for the two features (retrieve Popular movies, and Search by Keyword)).

I use Single and not Observable for the calls, because I only want one result, or error (http://angusmorton.com/rx-single/).
