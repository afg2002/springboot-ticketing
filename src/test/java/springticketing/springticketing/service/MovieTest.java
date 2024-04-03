package springticketing.springticketing.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import springticketing.springticketing.models.Movie;
import springticketing.springticketing.repository.BookingRepository;
import springticketing.springticketing.repository.MovieRepository;
import springticketing.springticketing.service.impl.UserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SpringBootTest
public class MovieTest {


    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserServiceImpl userService;

    @Autowired
    private BookingRepository bookingRepository;


    @Test
    public void testSaveMovies() throws ParseException {
        // Film 1
        Movie movie1 = new Movie();
        movie1.setTitle("The Batman");
        movie1.setPoster("https://m.media-amazon.com/images/M/MV5BM2MyNTAwZGEtNTAxNC00ODVjLTgzZjUtYmU0YjAzNmQyZDEwXkEyXkFqcGdeQXVyNDc2NTg3NzA@._V1_FMjpg_UX1000_.jpg");
        movie1.setDescription("Matt Reeves kembali dengan film Batman yang baru, dibintangi Robert Pattinson sebagai Caped Crusader.");
        movie1.setGenre("Action, Crime, Drama");
        String releaseDateString1 = "2022-03-04";
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate1 = dateFormat1.parse(releaseDateString1);
        movie1.setReleaseDate(releaseDate1);
        movie1.setRating(8.0);
        movieRepository.save(movie1);

        // Film 2
        Movie movie2 = new Movie();
        movie2.setTitle("Toy Story");
        movie2.setPoster("https://m.media-amazon.com/images/M/MV5BMDU2ZWJlMjktMTRhMy00ZTA5LWEzNDgtYmNmZTEwZTViZWJkXkEyXkFqcGdeQXVyNDQ2OTk4MzI@._V1_FMjpg_UX1000_.jpg");
        movie2.setDescription("Sebuah boneka koboi sangat terancam dan cemburu ketika figur astronot baru menggantikannya sebagai mainan teratas di kamar anak laki-laki.");
        movie2.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString2 = "1995-11-22";
        SimpleDateFormat dateFormat2 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate2 = dateFormat2.parse(releaseDateString2);
        movie2.setReleaseDate(releaseDate2);
        movie2.setRating(8.3);
        movieRepository.save(movie2);

        // Film 3
        Movie movie3 = new Movie();
        movie3.setTitle("Finding Nemo");
        movie3.setPoster("https://m.media-amazon.com/images/S/pv-target-images/0be03cd19aa100e1bde30c69b45f29ae4148a88b8e706dfa99f8193129e38ecd.jpg");
        movie3.setDescription("Setelah anaknya ditangkap di Great Barrier Reef dan dibawa ke akuarium dokter gigi, seekor ikan badut yang pemalu memulai perjalanan untuk membawanya pulang.");
        movie3.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString3 = "2003-05-30";
        SimpleDateFormat dateFormat3 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate3 = dateFormat3.parse(releaseDateString3);
        movie3.setReleaseDate(releaseDate3);
        movie3.setRating(8.1);
        movieRepository.save(movie3);

        // Film 4
        Movie movie4 = new Movie();
        movie4.setTitle("Up");
        movie4.setPoster("https://m.media-amazon.com/images/S/pv-target-images/28dd943b7c4762f91bf3626ea1b8377dcd19634ca47b30d629126fb3f6d481d5.jpg");
        movie4.setDescription("Carl Fredricksen berusia tujuh puluh delapan tahun melakukan perjalanan ke Paradise Falls di rumahnya yang dilengkapi balon, tanpa disadari membawa seorang anak muda sebagai penumpang gelap.");
        movie4.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString4 = "2009-05-29";
        SimpleDateFormat dateFormat4 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate4 = dateFormat4.parse(releaseDateString4);
        movie4.setReleaseDate(releaseDate4);
        movie4.setRating(8.3);
        movieRepository.save(movie4);

        // Film 5
        Movie movie5 = new Movie();
        movie5.setTitle("Frozen");
        movie5.setPoster("https://m.media-amazon.com/images/M/MV5BMTQ1MjQwMTE5OF5BMl5BanBnXkFtZTgwNjk3MTcyMDE@._V1_FMjpg_UX1000_.jpg");
        movie5.setDescription("Ketika Ratu Elsa yang baru dinobatkan secara tidak sengaja menggunakan kekuatannya untuk mengubah segala sesuatu menjadi es untuk mengutuk rumahnya dalam musim dingin tanpa akhir, saudara perempuannya Anna bergabung dengan seorang pria gunung, rusa mainannya yang lucu, dan seorang manusia salju untuk mengubah kondisi cuaca.");
        movie5.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString5 = "2013-11-27";
        SimpleDateFormat dateFormat5 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate5 = dateFormat5.parse(releaseDateString5);
        movie5.setReleaseDate(releaseDate5);
        movie5.setRating(7.5);
        movieRepository.save(movie5);
        
        // Film 6
        Movie movie6 = new Movie();
        movie6.setTitle("The Lion King");
        movie6.setPoster("https://m.media-amazon.com/images/S/pv-target-images/f73c6dda1f651905fc22195977e2316ead0c1140f8dba16bcce94ae82e3920be.jpg");
        movie6.setDescription("Simba, seekor singa muda, yang bersemangat untuk menggantikan ayahnya sebagai Raja Hutan, menguji ambisi dan daya tarik di dalam dirinya sendiri.");
        movie6.setGenre("Animasi, Petualangan, Drama");
        String releaseDateString6 = "2019-07-19";
        SimpleDateFormat dateFormat6 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate6 = dateFormat6.parse(releaseDateString6);
        movie6.setReleaseDate(releaseDate6);
        movie6.setRating(7.1);
        movieRepository.save(movie6);

        // Film 7
        Movie movie7 = new Movie();
        movie7.setTitle("Coco");
        movie7.setPoster("https://m.media-amazon.com/images/I/81L8rK0PjbL._AC_UF894,1000_QL80_.jpg");
        movie7.setDescription("Seorang bocah muda dari keluarga musisi di Meksiko ingin mengikuti jejak idola musiknya, meskipun tradisi keluarga melarangnya.");
        movie7.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString7 = "2017-11-22";
        SimpleDateFormat dateFormat7 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate7 = dateFormat7.parse(releaseDateString7);
        movie7.setReleaseDate(releaseDate7);
        movie7.setRating(8.4);
        movieRepository.save(movie7);

        // Film 8
        Movie movie8 = new Movie();
        movie8.setTitle("Moana");
        movie8.setPoster("https://m.media-amazon.com/images/S/pv-target-images/855007570c679d2d1e117ea14b16edb424be0eee75bc4cba3eb4668ee27e67d0._UR1920,1080_.jpg");
        movie8.setDescription("Putri petualang Polinesia, Moana, memulai pencarian berani untuk menyelamatkan pulaunya. Selama perjalanan, ia bertemu dengan demi-god Maui, yang membantu dia dalam pencarian epiknya.");
        movie8.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString8 = "2016-11-23";
        SimpleDateFormat dateFormat8 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate8 = dateFormat8.parse(releaseDateString8);
        movie8.setReleaseDate(releaseDate8);
        movie8.setRating(7.6);
        movieRepository.save(movie8);

        // Film 9
        Movie movie9 = new Movie();
        movie9.setTitle("Shrek");
        movie9.setPoster("https://m.media-amazon.com/images/S/pv-target-images/2476db59cb45005253032a577c62ab5fdfc90d62e3f6e9a471498595a4dac033.jpg");
        movie9.setDescription("Seorang ogre bermuka hijau bernama Shrek membawa sekelompok karakter dongeng untuk menyelamatkan putri dari penyamaran jahat dan menemukan kebenaran sejati tentang dirinya sendiri.");
        movie9.setGenre("Animasi, Petualangan, Komedi");
        String releaseDateString9 = "2001-05-18";
        SimpleDateFormat dateFormat9 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate9 = dateFormat9.parse(releaseDateString9);
        movie9.setReleaseDate(releaseDate9);
        movie9.setRating(7.8);
        movieRepository.save(movie9);

        // Film 10
        Movie movie10 = new Movie();
        movie10.setTitle("Beauty and the Beast");
        movie10.setPoster("data:image/jpeg;base64,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");
        movie10.setDescription("Untuk membebaskan ayahnya yang ditawan oleh Monster di Istana Monster, seorang gadis muda yang cantik bernama Belle menawarkan diri menjadi tahanan abadi di tempat ayahnya, tanpa menyadari bahwa Monster itu sebenarnya seorang pangeran yang terkutuk.");
        movie10.setGenre("Musikal, Romantis, Fantasi");
        String releaseDateString10 = "1991-11-22";
        SimpleDateFormat dateFormat10 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate10 = dateFormat10.parse(releaseDateString10);
        movie10.setReleaseDate(releaseDate10);
        movie10.setRating(8.0);
        movieRepository.save(movie10);
        
        // Film 11
        Movie movie11 = new Movie();
        movie11.setTitle("Aladdin");
        movie11.setPoster("https://m.media-amazon.com/images/M/MV5BNGM2MjY3ZGQtNTUxOC00ZjlmLTgxOTMtZTViOTRjODhjODZiXkEyXkFqcGdeQXVyNjE2MjQwNTI@._V1_FMjpg_UX1000_.jpg");
        movie11.setDescription("Ketika seorang jalanan muda menemukan lampu ajaib yang dapat menjadikan impian menjadi kenyataan, ia menggunakan lampu tersebut untuk mendapatkan cinta seorang putri.");
        movie11.setGenre("Musikal, Petualangan, Drama");
        String releaseDateString11 = "2019-05-24";
        SimpleDateFormat dateFormat11 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate11 = dateFormat11.parse(releaseDateString11);
        movie11.setReleaseDate(releaseDate11);
        movie11.setRating(7.0);
        movieRepository.save(movie11);

        // Film 12
        Movie movie12 = new Movie();
        movie12.setTitle("The Little Mermaid");
        movie12.setPoster("https://m.media-amazon.com/images/M/MV5BZmE2ZGJkYzctM2RkOC00YTIzLWE3ZDItOTIwNWI4ZTc5Njk3XkEyXkFqcGdeQXVyMTQxNzMzNDI@._V1_FMjpg_UX1000_.jpg");
        movie12.setDescription("Seorang putri duyung muda bersemangat untuk menjelajahi dunia manusia di atas permukaan laut, meskipun dilarang oleh ayahnya, Raja Triton.");
        movie12.setGenre("Animasi, Petualangan, Musikal");
        String releaseDateString12 = "1989-11-17";
        SimpleDateFormat dateFormat12 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate12 = dateFormat12.parse(releaseDateString12);
        movie12.setReleaseDate(releaseDate12);
        movie12.setRating(7.6);
        movieRepository.save(movie12);

        // Film 13
        Movie movie13 = new Movie();
        movie13.setTitle("Mulan");
        movie13.setPoster("https://m.media-amazon.com/images/M/MV5BYWUzN2JjYTItYmNkNi00NzU0LTg2NzYtNjU4OTM0ZTdmNjJlXkEyXkFqcGdeQXVyODEzNjM5OTQ@._V1_FMjpg_UX1000_.jpg");
        movie13.setDescription("Seorang gadis muda Cina menyamar sebagai prajurit pria untuk menyelamatkan ayahnya dari wajib militer, dan langsung menjadi pahlawan di mata bangsanya.");
        movie13.setGenre("Aksi, Petualangan, Drama");
        String releaseDateString13 = "1998-06-19";
        SimpleDateFormat dateFormat13 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate13 = dateFormat13.parse(releaseDateString13);
        movie13.setReleaseDate(releaseDate13);
        movie13.setRating(7.6);
        movieRepository.save(movie13);

        // Film 14
        Movie movie14 = new Movie();
        movie14.setTitle("Tarzan");
        movie14.setPoster("https://m.media-amazon.com/images/M/MV5BZDY1YjNlMzItNjIxOC00MjkwLTk3ZGItMDIyNTJmOGQyNjU4XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_FMjpg_UX1000_.jpg");
        movie14.setDescription("Anak lelaki yang dibesarkan oleh gorila di hutan Afrika menyadari bahwa dia sebenarnya manusia dan kembali ke dunia manusia untuk menemukan tempatnya.");
        movie14.setGenre("Animasi, Petualangan, Drama");
        String releaseDateString14 = "1999-06-18";
        SimpleDateFormat dateFormat14 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate14 = dateFormat14.parse(releaseDateString14);
        movie14.setReleaseDate(releaseDate14);
        movie14.setRating(7.3);
        movieRepository.save(movie14);

        // Film 15
        Movie movie15 = new Movie();
        movie15.setTitle("Pocahontas");
        movie15.setPoster("https://m.media-amazon.com/images/M/MV5BNDZkNzJjMmItMjY2Ny00NTdhLTg1ZmMtYjQ2NDA5MzkwOTM2XkEyXkFqcGdeQXVyNTA4NzY1MzY@._V1_FMjpg_UX1000_.jpg");
        movie15.setDescription("Seorang putri asli Amerika bertemu dengan pelaut Inggris yang datang untuk menemukan 'Emas Baru', dan kemudian menyelamatkan kehidupannya.");
        movie15.setGenre("Animasi, Petualangan, Drama");
        String releaseDateString15 = "1995-06-23";
        SimpleDateFormat dateFormat15 = new SimpleDateFormat("yyyy-MM-dd");
        Date releaseDate15 = dateFormat15.parse(releaseDateString15);
        movie15.setReleaseDate(releaseDate15);
        movie15.setRating(6.7);
        movieRepository.save(movie15);

    }

}
