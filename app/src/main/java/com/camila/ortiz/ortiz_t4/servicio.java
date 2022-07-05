package com.camila.ortiz.ortiz_t4;

import com.camila.ortiz.ortiz_t4.examenfinal.bodyImage;
import com.camila.ortiz.ortiz_t4.examenfinal.libro;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface servicio {

    @POST("N00029592")
    Call<Void> publicarPelicula(@Body pelicuala pelicuala);

    @GET("N00029592")
    Call<List<pelicualaGET>> mostrrListaPelicualas();

    @POST("image")
    Call<responseBody> imageUpload(@Header("Authorization") String token, @Body bodyImage image);

    @POST("/Libros")
    Call<Void> postCreatelibro(@Body libro libro);

    @GET("/Libros")
    Call<List<libro>> getListLibros();

    @GET("/Libros/{id}")
    Call<libro> getLibro(@Path("id") String id);

    @PUT("/Libros/{id}")
    Call<ResponseBody> updateLibro(@Body libro libro, @Path("id") String id);

    @POST("/favoritos")
    Call<libro> postCreateFavorito(@Body libro libro);

    @DELETE("/favoritos/{id}")
    Call<ResponseBody> deleteFavorito(@Path("id") String id);

    @GET("/favoritos")
    Call<List<libro>> getListFavoritos();

}
