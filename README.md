# TheMovies
 
Fitur:

* Menampilkan daftar genre resmi untuk film.
* Menampilkan daftar film menemukan berdasarkan genre.
* Tampilkan informasi utama tentang film ketika pengguna mengklik salah satu film.
* Tampilkan ulasan pengguna untuk sebuah film.
* Tampilkan trailer YouTube dari film.
* Implementasikan pengguliran tak terbatas pada daftar film dan ulasan pengguna.
* Menutupi kasus positif dan negatif.


Menggunakan API

Link API https://www.themoviedb.org/

API KEY

```
$API_KEY = ""
$page = 1
```


List Genre
```
https://api.themoviedb.org/3/genre/movie/list?api_key=$API_KEY&page=$page&language=en-US

```

Movie By Genre
```
$genreId = Int

https://api.themoviedb.org/3/discover/movie?api_key=$API_KEY&with_genres=$genreId&page=$page&language=en-US

```

Movie Detail
```
$movieId = Int

https://api.themoviedb.org/3/movie/$movieId?api_key=$API_KEY&language=en-US

```

List Trailer By Movie
```
$movieId = Int

http://api.themoviedb.org/3/movie/$movieId/videos?api_key=$API_KEY&language=en-US

```

List Review By Movie
```
$movieId = Int

https://api.themoviedb.org/3/movie/$movieId/reviews?api_key=$API_KEY&page=$page&language=en-US

```



Screenshot

<img src="https://raw.githubusercontent.com/ekohendratno/TheMovies/main/screenshot/img1.jpg" width="23%"></img> 
<img src="https://raw.githubusercontent.com/ekohendratno/TheMovies/main/screenshot/img2.jpg" width="23%"></img> 
<img src="https://raw.githubusercontent.com/ekohendratno/TheMovies/main/screenshot/img3.jpg" width="23%"></img> 
<img src="https://raw.githubusercontent.com/ekohendratno/TheMovies/main/screenshot/img4.jpg" width="23%"></img> 

