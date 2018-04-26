package xyz.alex.chess;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static org.testng.Assert.assertEquals;

/**
 * @author dumas45
 */
public class PackageTest {
    @DataProvider(name = "completeGames")
    public Object[][] getDataFor_completeGames() {
        return new Object[][] {
                {
                        "1. e4 g5 2. Bc4 f5 3. exf5 Nh6 4. Qh5+ Nf7 5. Bxf7#",
                        "rnbqkb1r/pppppB1p/8/5PpQ/8/8/PPPP1PPP/RNB1K1NR b KQkq - 0 5"
                },
                {
                        "1. e4 e6 2. d4 a6 3. a4 h6 4. Nf3 b6 5. c4 Bb7 6. Nc3 Nf6 7. e5 Nh5 8. g4 g6 9. gxh5 g5 " +
                                "10. Bd3 g4 11. Be4 Bxe4 12. Nxe4 gxf3 13. Qxf3 c6 14. Nf6+ Ke7 15. Ne4 Ke8 " +
                                "16. Rg1 Be7 17. Rg7 Rf8 18. Bxh6 d6 19. O-O-O Kd7 20. exd6 Bh4 21. Rxf7+ Rxf7 " +
                                "22. Qxf7+ Kc8 23. Rg1 Nd7 24. Rg8 Kb7 25. Rxd8 Rxd8 26. Qf4 Bf6 27. Nxf6 Nxf6 " +
                                "28. Qxf6 Rd7 29. c5 bxc5 30. dxc5 a5 31. Qe7 Rxe7 32. dxe7 Ka6 33. e8=Q Ka7 " +
                                "34. Qxc6 e5 35. Bf8 e4 36. Bd6 e3 37. Qb6+ Ka8 38. Qb8#",
                        "kQ6/8/3B4/p1P4P/P7/4p3/1P3P1P/2K5 b - - 3 38"
                },
                {
                        "1.e4 c5 2.Nf3 Nc6 3.d4 cxd4 4.Nxd4 e5 5.Nb5 d6 6.N1c3 a6 7.Na3 Be7 8.Nc4 \n" +
                                "b5 9.Ne3 Nf6 10.Bd3 Nb4 11.Be2 Be6 12.a3 Nc6 13.Ned5 Nd4 14.Bd3 O-O 15.Be3\n" +
                                "Nxd5 16.Nxd5 Bg5 17.c3 Bxe3 18.Nxe3 Nc6 19.O-O Ne7 20.a4 bxa4 21.Rxa4 a5 \n" +
                                "22.Bc4 Bd7 23.Ra2 Bc6 24.Qd3 Qc7 25.b3 h6 26.f3 Kh8 27.Rd1 Rad8 28.Rda1 \n" +
                                "Ra8 29.Kh1 f5 30.exf5 d5 31.Bxd5 Bxd5 32.Nxd5 Nxd5 33.Qxd5 Qxc3 34.h3 Rad8\n" +
                                "35.Qxa5 Qxb3 36.Qxe5 Rd5 37.Qe6 Qb5 38.f6 Rxf6 39.Ra8+ Kh7 40.Qg8+ Kg6 41.\n" +
                                "R8a7 Rd7 42.Qe8+ Rff7 43.Qe4+",
                        "8/R2r1rp1/6kp/1q6/4Q3/5P1P/6P1/R6K b - - 9 43"
                },
                {
                        "1.e4 e5 2.Nf3 Nc6 3.d4 exd4 4.Nxd4 Bc5 5.Nxc6 Qf6 6.Qf3 bxc6 7.Nd2 d6 8.\n" +
                                "Nb3 Bb6 9.Qg3 Ne7 10.Be2 O-O 11.Bd2 a5 12.Bc3 Qg6 13.Bd3 f5 14.f3 Qh6 15.\n" +
                                "Nd4 f4 16.Qf2 c5 17.Bc4+ d5 18.exd5 cxd4 19.d6+ Be6 20.dxe7 Bxc4 21.\n" +
                                "exf8=Q+ Rxf8 22.Bxd4 Re8+ 23.Kd2 Rd8 24.c3 c5",
                        "3r2k1/6pp/1b5q/p1p5/2bB1p2/2P2P2/PP1K1QPP/R6R w - c6 0 25"
                },
                {
                        "1.e3 e5 2.Nf3 e4 3.Nd4 d5 4.h3 c5 5.Bb5+ Nd7 6.Bxd7+ Bxd7 7.Ne2 Qc8 8.Nf4 Nf6 9.Nc3 d4 " +
                                "10.Ncd5 Nxd5 11.Nxd5 Bd6 12.Qh5 d3 13.cxd3 exd3 14.Qg5 O-O 15.Nf6+ Kh8 " +
                                "16.Nh5 Rg8 17.b3 b5 18.Bb2 Bf8 19.Qf5 Bxf5 20.O-O-O c4 21.bxc4 Qxc4+ 22.Kb1 Be6 " +
                                "23.Rc1 Qxa2#",
                        "r4brk/p4ppp/4b3/1p5N/8/3pP2P/qB1P1PP1/1KR4R w - - 0 24"
                },
                {
                        "1.e4 c5 2.Nf3 d6 3.Bb5+ Nd7 4.O-O Nf6 5.d4 a6 6.Bxd7+ Nxd7 7.Nc3 e6 8.d5 \n" +
                                "e5 9.a4 Be7 10.Nd2 O-O 11.Nc4 Nb6 12.Ne3 Bg5 13.b3 Bxe3 14.Bxe3 f5 15.f4 \n" +
                                "Nd7 16.a5 Qe7 17.Qd2 fxe4 18.fxe5 Qxe5 19.Rxf8+ Nxf8 20.Bf4 Qe7 21.Na4 Bf5\n" +
                                "22.Nb6 Rd8 23.Bg5 e3 24.Bxe7 exd2 25.Bxd8 Bxc2 26.Be7 d1=Q+ 27.Rxd1 Bxd1 \n" +
                                "28.Bxd6 Bxb3 29.Bxc5 Kf7 30.Bxf8 Kxf8 31.Kf2 Ke7 32.Ke3 Kd6 33.Kd4 g5 34.\n" +
                                "g3 g4 35.Nc4+ Bxc4 36.Kxc4 Kd7 37.Kc5 Kc7 38.d6+ Kd7 39.Kd5 h6 40.Ke5",
                        "8/1p1k4/p2P3p/P3K3/6p1/6P1/7P/8 b - - 1 40"
                },
                {
                        "1.d4 d5 2.c4 c6 3.Nf3 Nf6 4.e3 Bf5 5.Nc3 a6 6.Be2 h6 7.Bd3 Bxd3 8.Qxd3 e6 \n" +
                                "9.O-O Bb4 10.Bd2 O-O 11.Rfd1 Bxc3 12.Bxc3 Nbd7 13.b3 Qe7 14.Rac1 Rac8 15.\n" +
                                "Qe2 Ne4 16.Bb2 Rfd8 17.Ne1 Nd6 18.Ba3 f5 19.Nd3 Nf6 20.Bb4 Qc7 21.Qf3 dxc4\n" +
                                "22.bxc4 Nf7 23.a4 a5 24.Be1 b6 25.Qg3 Qxg3 26.hxg3 Ra8 27.f3 Rdb8 28.Rc2 \n" +
                                "b5 29.Nc5 bxc4 30.Rxc4 Nd5 31.Bd2 e5 32.e4 fxe4 33.Nxe4 Nb6 34.Rxc6 Nd8 \n" +
                                "35.Rg6 Nc4 36.dxe5 Kh7 37.Rg4 Nxe5 38.Rh4 Ndf7 39.Bc3 Rb3 40.Rd5 Re8 41.\n" +
                                "Rf4 Re7 42.Bxa5 Ng6 43.Rff5 Nfe5 44.Rd1 Nc4 45.Rc1 Nxa5 46.Rxa5 Ra3 47.\n" +
                                "Rcc5 Ra2 48.Kh2 Rd7 49.Ra6 Ne7 50.g4 Rb7 51.Rb5 Rc7 52.Nc5 Rc6 53.Rxc6 \n" +
                                "Nxc6 54.Rb7 Nd4 55.Kh3 Kg8 56.Rb4 Ne2 57.g5 Ng1+ 58.Kg3 Ne2+ 59.Kg4 hxg5 \n" +
                                "60.Kxg5 Ng1 61.Rg4",
                        "6k1/6p1/8/2N3K1/P5R1/5P2/r5P1/6n1 b - - 2 61"
                },
                {
                        "1. e4 e5 2. Nf3 Nc6 3. Bb5 a6 4. Ba4 Nf6 5. O-O Nxe4 6. d4 b5 7. Bb3 d5 8. dxe5  Be6 " +
                                "9. c3 Be7 10. Nbd2 O-O 11. Re1 Nc5 12. Bc2 d4 13. cxd4 Nxd4 14. Nxd4 Qxd4  " +
                                "15. Nb3 Nxb3 16. axb3 Qxd1 17. Rxd1 c5 18. Bd2 Rfd8 19. Ba5 Rxd1+ 20. Rxd1 f6  " +
                                "21. Bc3 fxe5 22. Bxe5 Rd8 23. Rxd8+ Bxd8 24. f4 Kf7 25. Kf2 Bf6 26. Bd6 Bd4+  " +
                                "27. Kf3 Bd5+ 28. Kg4 Ke6 29. Bf8 Kf7 30. Bd6 Bxg2 31. Bxh7 Ke6 32. Bf8 Kd5 " +
                                "33.  Kg5 Bf6+ 34. Kg6 Be4+ 35. f5 Ke5 36. Bxg7 Bxf5+ 37. Kf7 Bxg7 38. Bxf5 Kxf5 " +
                                "39.  Kxg7 a5 40. h4 Kg4 41. Kg6 Kxh4 42. Kf5 Kg3 43. Ke4 Kf2 44. Kd5 Ke3 " +
                                "45. Kxc5  Kd3 46. Kxb5 Kc2 47. Kxa5 Kxb3",
                        "8/8/8/K7/8/1k6/1P6/8 w - - 0 48"
                },
                {
                        "1.e4 e5 2.Nf3 Nc6 3.Bb5 Nf6 4.O-O Nxe4 5.d4 Nd6 6.Bxc6 dxc6 7.dxe5 Nf5 8.\n" +
                                "Qxd8+ Kxd8 9.h3 Ke8 10.Nc3 h5 11.Bf4 Be7 12.Rad1 Be6 13.Ng5 Rh6 14.g3 Bxg5\n" +
                                "15.Bxg5 Rg6 16.h4 f6 17.exf6 gxf6 18.Bf4 Nxh4 19.f3 Rd8 20.Kf2 Rxd1 21.\n" +
                                "Nxd1 Nf5 22.Rh1 Bxa2 23.Rxh5 Be6 24.g4 Nd6 25.Rh7 Nf7 26.Ne3 Kd8 27.Nf5 c5\n" +
                                "28.Ng3 Ne5 29.Rh8+ Rg8 30.Bxe5 fxe5 31.Rh5 Bxg4 32.fxg4 Rxg4 33.Rxe5 b6 \n" +
                                "34.Ne4 Rh4 35.Ke2 Rh6 36.b3 Kd7 37.Kd2 Kc6 38.Nc3 a6 39.Re4 Rh2+ 40.Kc1 \n" +
                                "Rh1+ 41.Kb2 Rh6 42.Nd1 Rg6 43.Ne3 Rh6 44.Re7 Rh2 45.Re6+ Kb7 46.Kc3 Rh4 \n" +
                                "47.Kb2 Rh2 48.Nd5 Rd2 49.Nf6 Rf2 50.Kc3 Rf4 51.Ne4 Rh4 52.Nf2 Rh2 53.Rf6 \n" +
                                "Rh7 54.Nd3 Rh3 55.Kd2 Rh2+ 56.Rf2 Rh4 57.c4 Rh3 58.Kc2 Rh7 59.Nb2 Rh5 60.\n" +
                                "Re2 Rg5 61.Nd1 b5 62.Nc3 c6 63.Ne4 Rh5 64.Nf6 Rg5 65.Re7+ Kb6 66.Nd7+ Ka5 \n" +
                                "67.Re4 Rg2+ 68.Kc1 Rg1+ 69.Kd2 Rg2+ 70.Ke1 bxc4 71.Rxc4 Rg3 72.Nxc5 Kb5 \n" +
                                "73.Rc2 a5 74.Kf2 Rh3 75.Rc1 Kb4 76.Ke2 Rc3 77.Nd3+ Kxb3 78.Ra1 Kc4 79.Nf2 \n" +
                                "Kb5 80.Rb1+ Kc4 81.Ne4 Ra3 82.Nd2+ Kd5 83.Rh1 a4 84.Rh5+ Kd4 85.Rh4+ Kc5 \n" +
                                "86.Kd1 Kb5 87.Kc2 Rg3 88.Ne4 Rg2+ 89.Kd3 a3 90.Nc3+ Kb6 91.Ra4 a2 92.Nxa2 \n" +
                                "Rg3+ 93.Kc2 Rg2+ 94.Kb3 Rg3+ 95.Nc3 Rh3 96.Rb4+ Kc7 97.Rg4 Rh7 98.Kc4 Rf7 \n" +
                                "99.Rg5 Kb6 100.Na4+ Kc7 101.Kc5 Kd7 102.Kb6 Rf1 103.Nc5+ Ke7 104.Kxc6 Rd1 \n" +
                                "105.Rg6 Kf7 106.Rh6 Rg1 107.Kd5 Rg5+ 108.Kd4 Rg6 109.Rh1 Rg2 110.Ne4 Ra2 \n" +
                                "111.Rf1+ Ke7 112.Nc3 Rh2 113.Nd5+ Kd6 114.Rf6+ Kd7 115.Nf4 Rh1 116.Rg6 \n" +
                                "Rd1+ 117.Nd3 Ke7 118.Ra6 Kd7 119.Ke4 Ke7 120.Rc6 Kd7 121.Rc1 Rxc1 122.Nxc1",
                        "8/3k4/8/8/4K3/8/8/2N5 b - - 0 122"
                },
                {
                        "1.b3 g6 2.Bb2 Nf6 3.e4 d6 4.g3 Bg7 5.Bg2 O-O 6.d4 c5 7.dxc5 Qa5+ 8.Qd2 \n" +
                                "Qxc5 9.c4 Nc6 10.Nc3 Qd4 11.Qxd4 Nxd4 12.O-O-O Ne6 13.h3 Nc5 14.Nge2 a5 \n" +
                                "15.Ba3 Na6 16.e5 dxe5 17.Bxe7 Re8 18.Bxf6 Bxf6 19.Ne4 Be7 20.Nd6 Bxd6 21.\n" +
                                "Rxd6 a4 22.Nc3 axb3 23.axb3 Kg7 24.Kb2 Nc5 25.Rd5 Na6 26.Rhd1 Nb4 27.Rd6 \n" +
                                "Ra6 28.Rxa6 bxa6 29.Ne4 Re7 30.Nd6 Be6 31.c5 Kf8 32.Bf1 Rc7 33.Rc1 a5 34.\n" +
                                "Ka3 Ke7 35.f4 exf4 36.gxf4 Nc6 37.Nb5 Rd7 38.Nd6 Rc7 39.Nc4 Nd4 40.Nd2 Bd7\n" +
                                "41.Ne4 Ne6 42.Re1 Nxf4 43.h4 Ne6 44.Bc4 h6 45.b4 axb4+ 46.Kxb4 Ra7 47.h5 \n" +
                                "gxh5 48.Ng3 h4 49.Nf5+ Kf6 50.Nxh4 Ra4+ 51.Kc3 Nxc5 52.Rf1+ Ke5 53.Nf3+ \n" +
                                "Kd6 54.Bxf7 Rf4 55.Rd1+ Ke7 56.Bd5 Be6 57.Ne5 Kf6 58.Bxe6 Nxe6 59.Nd3 Rf3 \n" +
                                "60.Rh1 Kg5 61.Kd2 h5 62.Rg1+ Kh6 63.Ne5 Rf6 64.Ng4+ hxg4 65.Rxg4 Ng5 66.\n" +
                                "Ke3 Kg6 67.Ra4 Rb6 68.Ra5 Kf6 69.Ra3 Rb5 70.Kd4 Ne6+ 71.Ke4 Rb4+ 72.Ke3 \n" +
                                "Nc7 73.Ra5 Ke6 74.Kd3 Nd5 75.Ra8 Nc7 76.Ra5 Rh4 77.Rc5 Kd6 78.Ra5 Ne6 79.\n" +
                                "Ra8 Kd5 80.Ra5+ Nc5+ 81.Ke3 Rc4 82.Ra8 Rc3+ 83.Kf4 Ne6+ 84.Kg4 Ke4 85.Ra4+\n" +
                                "Nd4 86.Ra8 Rc6 87.Re8+ Ne6 88.Re7 Ke5 89.Kf3 Rc3+ 90.Ke2 Ra3 91.Kd2 Kd5 \n" +
                                "92.Re8 Nc5 93.Rd8+ Kc4 94.Ke2 Rd3 95.Rxd3 Nxd3",
                        "8/8/8/8/2k5/3n4/4K3/8 w - - 0 96"
                },
                {
                        "1.e4 d5 2.exd5 Qxd5 3.Nc3 Qa5 4.Nf3 Nf6 5.d4 c6 6.Bc4 Bf5 7.O-O e6 8.Ne2 \n" +
                                "Nbd7 9.Ng3 Bg6 10.Bf4 Nd5 11.Bd2 Qc7 12.Bb3 Bd6 13.c4 N5f6 14.Re1 O-O 15.\n" +
                                "Nh4 c5 16.Nxg6 hxg6 17.dxc5 Nxc5 18.Bc2 Be5 19.Rb1 Rad8 20.Qe2 Bd4 21.b4 \n" +
                                "Ncd7 22.Bb3 a5 23.a3 axb4 24.axb4 Ra8 25.Qd3 Ba7 26.Bc3 Ng4 27.c5 Ndf6 28.\n" +
                                "Qf3 b6 29.h3 Nxf2 30.Qxf2 bxc5 31.Be5 Qe7 32.bxc5 Bxc5 33.Bd4 Bxd4 34.Qxd4\n" +
                                "Rfd8 35.Qf2 Rd3 36.Bc4 Rc3 37.Rbc1 Rxc1 38.Rxc1 Rc8 39.Rc2 Qb4 40.Bf1 Rxc2\n" +
                                "41.Qxc2 Nd5 42.Qf2 Qb1 43.Kh2 Qb8 44.Bc4 Nf6 45.Qe3 Qc7 46.Be2 Nd5 47.Qd4 \n" +
                                "Ne7 48.Bd3 Nd5 49.Be4 Nf6 50.Bf3 Nd7 51.h4 Nf6 52.Qe3 Nd7 53.h5 gxh5 54.\n" +
                                "Bxh5 Nf6 55.Bf3 Nd7 56.Qg5 Ne5 57.Be4 f5 58.Bf3 Qd6 59.Kh3 Nf7 60.Qg6 Qe7 \n" +
                                "61.Nh5 Ng5+ 62.Kg3 Nxf3 63.gxf3 Qf7 64.Qg5 e5 65.Qd8+ Kh7 66.Qg5 Qg6 67.\n" +
                                "Kh4 e4 68.Qxg6+ Kxg6 69.f4 e3 70.Ng3 Kf6 71.Ne2 g6 72.Kg3 g5 73.fxg5+ Kxg5\n" +
                                "74.Kf3 f4 75.Nxf4 e2 76.Kxe2 Kxf4",
                        "8/8/8/8/5k2/8/4K3/8 w - - 0 77"
                },
                {
                        "1.e4 e6 2.d4 d5 3.Nc3 Bb4 4.exd5 exd5 5.Bd3 c6 6.Ne2 Ne7 7.O-O O-O 8.Ng3 \n" +
                                "Nd7 9.Bg5 Qc7 10.a3 Bd6 11.Qh5 Ng6 12.Nf5 Re8 13.Rae1 Rxe1 14.Rxe1 Nf6 15.\n" +
                                "Bxf6 Bxf5 16.Bxf5 gxf6 17.Re3 Kg7 18.Rh3 Rh8 19.Qh6+ Kg8 20.Re3 Qd8 21.Ne2\n" +
                                "Bf8 22.Qh5 Qd6 23.g3 b6 24.h4 Qd8 25.Qf3 Bh6 26.Rc3 Qd6 27.Qg4 Kf8 28.Kf1 \n" +
                                "Rg8 29.Bxg6 hxg6 30.Qc8+ Kg7 31.Qxc6 Qd8 32.Rf3 Re8 33.Nc3 Bd2 34.Ne2 Qe7 \n" +
                                "35.Qb5 Qe6 36.Ng1 Re7 37.Qe2 Bc1 38.Qxe6 fxe6 39.Ne2 Bd2 40.Rd3 Bh6 41.Rc3\n" +
                                "Kf7 42.Rc8 Bd2 43.a4 Rd7 44.f4 Bb4 45.Ng1 Bd6 46.Nf3 Kg7 47.Ke2 Kf7 48.b3 \n" +
                                "Bb4 49.c3 Bd6 50.Ke3 Ke7 51.Rh8 Kf7 52.Rh7+ Ke8 53.Rxd7 Kxd7 54.g4 Ke7 55.\n" +
                                "c4 Kf7 56.cxd5 exd5 57.h5 gxh5 58.gxh5 Kg7 59.Nh4 Bf8 60.Nf5+ Kh7 61.Kf3 \n" +
                                "Bb4 62.Ne3 Bc3 63.Nxd5 Bxd4 64.Ke4 Bb2 65.Kf5 Kh6 66.Nxf6 Bc3 67.Ke6",
                        "8/p7/1p2KN1k/7P/P4P2/1Pb5/8/8 b - - 2 67"
                },
                {
                        "1.e4 e6 2.d4 d5 3.exd5 exd5 4.Nf3 Nf6 5.Bd3 Bd6 6.Qe2+ Be6 7.Ng5 Qe7 8.\n" +
                                "Nxe6 Qxe6 9.O-O Qxe2 10.Bxe2 O-O 11.c3 Nbd7 12.Rd1 Rfe8 13.Bd3 c6 14.Nd2 \n" +
                                "Bf4 15.g3 Bh6 16.b3 a5 17.a4 g6 18.Ba3 Re6 19.Nf3 Ne4 20.c4 Nef6 21.Re1 \n" +
                                "Rae8 22.Rxe6 Rxe6 23.Bb2 Ne4 24.Kg2 Nd2 25.Nxd2 Bxd2 26.Rd1 Bh6 27.Bc3 b6 \n" +
                                "28.cxd5 cxd5 29.b4 axb4 30.Bxb4 Nb8 31.Bb5 Nc6 32.Bxc6 Rxc6 33.Re1 Rc8 34.\n" +
                                "Re5 Bf8 35.Bd2 f6 36.Rxd5 Rc4 37.a5 bxa5 38.Bxa5 Kf7 39.Rd7+ Ke6 40.Rxh7 \n" +
                                "Rxd4 41.Rb7 Rd5 42.Rb6+ Kf7 43.Bc3 Be7 44.Bb2 g5 45.g4 Rd3 46.h3 Rd2 47.\n" +
                                "Rb7 Ke6 48.Bc1 Rc2 49.Be3 Bd6 50.Rb6 Ke7 51.Bd4 Rd2 52.Be3 Rd3 53.Ra6 Be5 \n" +
                                "54.Ra5 Ke6 55.Ra6+ Rd6 56.Rxd6+ Kxd6 57.Kf3 Kd5 58.Ke2 Kc4 59.Bb6 Kd5 60.\n" +
                                "Kd3 Ke6 61.Bd4 Bd6 62.Ke4 Bc7 63.Be3 Bd6 64.f3 Bc7 65.f4 Bd8 66.Bc1 Be7 \n" +
                                "67.Kf3 Kf7 68.Kg3 Bd6 69.Kf2 Be7 70.Bd2 Ke6 71.Kf3 Kf7 72.Ke4 Ke6 73.Be1 \n" +
                                "Bd8 74.h4 gxh4 75.Bxh4 Ba5 76.Be1 Bxe1 77.Kd4 Bd2 78.Ke4 Bc1",
                        "8/8/4kp2/8/4KPP1/8/8/2b5 w - - 4 79"
                },
                {
                        "1.e4 e6 2.d4 d5 3.exd5 exd5 4.Nf3 Bg4 5.h3 Bh5 6.Qe2+ Qe7 7.Be3 Nc6 8.Nc3 \n" +
                                "O-O-O 9.g4 Bg6 10.O-O-O f6 11.a3 Qd7 12.Nd2 f5 13.Nb3 Nf6 14.f3 Bd6 15.Qd2\n" +
                                "Rhe8 16.Bg5 fxg4 17.hxg4 Qf7 18.Nb5 Kb8 19.Nxd6 cxd6 20.Bd3 Bxd3 21.Qxd3 \n" +
                                "h6 22.Bd2 Re6 23.Na5 Nxa5 24.Bxa5 Rde8 25.Bd2 Nd7 26.Rde1 Nf8 27.Rxe6 Rxe6\n" +
                                "28.Rh5 Rf6 29.f4 Ne6 30.f5 Nd8 31.b4 Nc6 32.b5 Ne7 33.a4 Nc8 34.a5 Qe8 35.\n" +
                                "Rh3 Rf7 36.Re3 Re7 37.Rxe7 Qxe7 38.Qf3 Qf7 39.Bb4 Kc7 40.Qc3+ Kd8 41.Qf3 \n" +
                                "Kc7 42.Kd2 Kd8 43.Kd1 Kc7 44.Be1 Ne7 45.a6 b6 46.Bh4 g5 47.Bf2 Qf6 48.Qh1 \n" +
                                "Kd7 49.c4 Ke8 50.Kd2 Kf7 51.cxd5 Kg7 52.Kd3 Qf7 53.Bg3 Qe8 54.Kc4 Nxf5 55.\n" +
                                "gxf5 Qe2+ 56.Kc3 Qe3+ 57.Kc4 Qe2+ 58.Kc3",
                        "8/p5k1/Pp1p3p/1P1P1Pp1/3P4/2K3B1/4q3/7Q b - - 6 58"
                },
                {
                        "1.e4 e6 2.d4 d5 3.exd5 exd5 4.Nf3 Bd6 5.h3 Ne7 6.c4 c6 7.c5 Bc7 8.Bd3 Bf5 \n" +
                                "9.O-O O-O 10.Re1 Bxd3 11.Qxd3 Nd7 12.Nc3 Ng6 13.Bg5 Nf6 14.Ne5 Bxe5 15.\n" +
                                "dxe5 h6 16.Bxf6 gxf6 17.e6 Re8 18.Re3 Kg7 19.Ne2 Rxe6 20.Nd4 Re5 21.Nf5+ \n" +
                                "Kh7 22.Rae1 d4 23.Rxe5 fxe5 24.g3 Qa5 25.b4 Qxb4 26.Rb1 Qc3 27.Qd1 e4 28.\n" +
                                "Rxb7 Rf8 29.Qh5 d3 30.Qxh6+ Kg8 31.h4 Qe5 32.Qg5 e3 33.Nxe3 Qxg5 34.hxg5 \n" +
                                "Rd8 35.f4 d2 36.Rb1 Nf8 37.Rd1 Ne6 38.Nf1 Rd3 39.Rxd2 Ra3 40.Re2 Nxc5 41.\n" +
                                "f5 Kf8 42.f6 Kg8 43.Re7 Rf3 44.Kg2 Rf5 45.Re8+ Kh7 46.Rf8 Rxg5 47.Rxf7+ \n" +
                                "Kg6 48.Rxa7 Kxf6 49.Rc7 Ne6 50.Rxc6 Ra5 51.Rc2 Ra3 52.Rf2+ Kg6 53.Rb2 Nc5 \n" +
                                "54.Nd2 Na4 55.Nc4 Rc3 56.Ne5+ Kf5 57.Re2 Nc5 58.Nf3 Ne4 59.Nh4+ Ke5 60.Kh2\n" +
                                "Ra3 61.Ng2 Kd4 62.Ne1 Ke5 63.Ng2 Kd4 64.g4 Nf6 65.g5 Ng4+ 66.Kg1 Rg3 67.\n" +
                                "Kf1 Nh2+ 68.Kg1 Ng4 69.Rd2+ Ke4 70.Rb2 Ne5 71.Rb4+ Kf5 72.Rb5 Rxg5 73.Kf1 \n" +
                                "Rg3 74.Ra5 Ke4 75.Kf2 Rc3 76.Ra4+ Rc4 77.Rxc4+ Nxc4 78.a3 Nxa3",
                        "8/8/8/8/4k3/n7/5KN1/8 w - - 0 79"
                },
                {
                        "1.e4 e6 2.d4 d5 3.Nc3 Bb4 4.exd5 exd5 5.Bd3 Nf6 6.Ne2 c6 7.O-O O-O 8.Ng3 \n" +
                                "Re8 9.Bf4 Bg4 10.f3 Be6 11.Nce2 Bd6 12.Qd2 c5 13.dxc5 Bxc5+ 14.Kh1 Nc6 15.\n" +
                                "a3 a5 16.Rae1 d4 17.Bg5 h6 18.Bxf6 Qxf6 19.Ne4 Qe7 20.Nf4 Bb6 21.Nxe6 Qxe6\n" +
                                "22.f4 Qd5 23.Qe2 Kf8 24.Qg4 Bc7 25.Qh4 Bd8 26.Qh3 Be7 27.Nd2 Bf6 28.Bc4 \n" +
                                "Qc5 29.Qd7 Re7 30.Rxe7 Qxe7 31.Qxe7+ Bxe7 32.g3 Rc8 33.Bd3 Nd8 34.Nc4 Rc5 \n" +
                                "35.a4 Nc6 36.b3 Nb4 37.Rd1 g5 38.fxg5 hxg5 39.Kg2 Kg7 40.Re1 Bf6 41.Be4 b5\n" +
                                "42.axb5 Rxb5 43.Rf1 Be7 44.Rf5 Rxf5 45.Bxf5 Bd8 46.Kf3 Kf6 47.Bd3 Ke6 48.\n" +
                                "Ke4 Nc6 49.g4 Bc7 50.h3 Bg3 51.Na3 Bf2 52.Nb5 f6 53.Nc7+ Kf7 54.Bc4+ Kg6 \n" +
                                "55.Nb5 Bg1 56.Bd3 Kf7 57.Kd5 Nb4+ 58.Kc4 Nc6 59.Be4 Ne5+ 60.Kd5 Nd7 61.Bf5\n" +
                                "Nb6+ 62.Kc6 a4 63.Bd3 axb3 64.cxb3 Nc8 65.b4 Ne7+ 66.Kd7 Kf8 67.Nc7 Bh2 \n" +
                                "68.Ne6+ Kf7 69.Bc4 Ng6 70.Nxd4+ Kg7 71.Nf3 Bg3 72.b5 Nf4 73.Nd4 Bf2 74.\n" +
                                "Nf5+ Kf8 75.Bf1 Kf7 76.Kc6 Ke6 77.b6 Ke5 78.b7 Ba7 79.Bc4 Nxh3 80.Kc7 Nf4 \n" +
                                "81.Ne7 Ne6+ 82.Bxe6 Kxe6 83.Nc6 Bf2 84.Kc8 Bg3 85.Nd4+ Kd5 86.Nb5 Kc6 87.\n" +
                                "Nc3 Be5 88.Ne4 f5 89.gxf5 g4 90.b8=Q Bxb8 91.Kxb8 Kd5 92.Ng3 Ke5 93.Kc7 \n" +
                                "Kf4 94.Kd6 Kxg3 95.f6 Kh2 96.f7 g3 97.f8=Q g2 98.Qh6+ Kg3 99.Qg5+ Kf2 100.\n" +
                                "Qf4+ Ke1 101.Qg3+ Kf1 102.Qf3+ Kg1 103.Ke5",
                        "8/8/8/4K3/8/5Q2/6p1/6k1 b - - 11 103"
                },
                {
                        "1.e4 e6 2.d4 d5 3.exd5 exd5 4.Nf3 Nf6 5.c4 Bb4+ 6.Nc3 O-O 7.Bd3 Re8+ 8.Be3\n" +
                                "dxc4 9.Bxc4 Be6 10.Bxe6 Rxe6 11.O-O Nbd7 12.Qb3 a5 13.d5 Re8 14.Rfd1 Ne5 \n" +
                                "15.Nxe5 Rxe5 16.Bd4 Rh5 17.h3 b6 18.d6 Qxd6 19.Bxf6 Qxf6 20.Nd5 Qh4 21.\n" +
                                "Nxc7 Rf8 22.Nd5 Bc5 23.Qf3 Rg5 24.Rd3 h6 25.Re1 Qc4 26.Nc3 Rg6 27.Re4 Qa6 \n" +
                                "28.Nd5 Qb5 29.Re2 Qc4 30.a3 Re6 31.Nc3 Rfe8 32.Rxe6 Qxe6 33.Rd1 Qe5 34.g3 \n" +
                                "Bd4 35.Rd3 Re6 36.Kg2 g6 37.Qa8+ Re8 38.Qc6 Bc5 39.Rf3 Qe6 40.Qb5 Rd8 41.\n" +
                                "Rd3 Rxd3 42.Qxd3 Qf6 43.Qe2 Bxa3 44.bxa3 Qxc3 45.Qe8+ Kg7 46.a4 h5 47.h4 \n" +
                                "Qd4 48.Kg1 Qd1+ 49.Kg2 Qd5+ 50.Kg1 Kf6 51.Qe2 Qe6 52.Qb2+ Ke7 53.Qd4 Qf6 \n" +
                                "54.Qe4+ Kd6 55.Qd3+ Kc7 56.Qc4+ Kb8 57.Qe4 Qe6 58.Qf4+ Kb7 59.Qf3+ Kc7 60.\n" +
                                "Qc3+ Kd6 61.Qd4+ Kc6 62.Qc3+ Kd5 63.Kh2 Kd6 64.Kg1 Qd5 65.Kh2 Qa2 66.Qd4+ \n" +
                                "Kc6 67.Qf6+ Qe6 68.Qc3+ Kb7 69.Qf3+ Ka7 70.Qc3 Qd7 71.Qc4 Kb7 72.Kg1 Qe6 \n" +
                                "73.Qc3 Qe4 74.Qb3 Qe1+ 75.Kg2 Qe6 76.Qa3 b5 77.axb5 Kb6 78.Qa4 Qd5+ 79.Kg1\n" +
                                "Qxb5 80.Qd4+ Kb7 81.Qe4+ Qc6 82.Qe7+ Qc7 83.Qe4+ Kb6 84.Qd4+ Kb5 85.Qd5+ \n" +
                                "Kb4 86.Qd4+ Qc4 87.Qb2+ Kc5 88.Qe5+ Qd5 89.Qc3+ Kd6 90.Qf6+ Kd7 91.Kh2 a4 \n" +
                                "92.Qf4 Qb3 93.Qd4+ Ke6 94.Qe4+ Kd6 95.Qd4+ Kc6 96.Qe4+ Kc5 97.Qe7+ Kd4 98.\n" +
                                "Qf6+ Kd3 99.Qf3+ Kd2 100.Qf4+ Ke2 101.Kg2 Qd5+ 102.Kg1 Qd1+ 103.Kh2 Kf1 \n" +
                                "104.Qc4+ Qe2 105.Qc1+ Kxf2 106.Qg1+ Kf3+ 107.Kh3 Qe6+ 108.Kh2 Qe2+ 109.Kh3\n" +
                                "Qc4 110.Qd1+ Ke4 111.Qd2 Kf5 112.Qf2+ Ke6 113.Qe3+ Kf6 114.Qf2+ Kg7 115.\n" +
                                "Qb2+ Kh7 116.Kh2 Qb3 117.Qf2 a3 118.Qa7 a2 119.Kh3 Qe6+ 120.Kh2 Qe2+ 121.\n" +
                                "Kg1 Qe1+ 122.Kh2 a1=Q 123.Qxf7+ Qg7",
                        "8/5Qqk/6p1/7p/7P/6P1/7K/4q3 w - - 1 124"
                },
                {
                        "1.e4 e6 2.d4 d5 3.exd5 exd5 4.Nf3 Nf6 5.h3 Bf5 6.Bd3 Be4 7.O-O Be7 8.Re1 \n" +
                                "Nc6 9.c3 O-O 10.Bg5 Re8 11.Bxf6 Bxf3 12.Qxf3 Bxf6 13.Na3 Ne7 14.Nc2 c6 15.\n" +
                                "Ne3 Ng6 16.Ng4 Qd6 17.Nxf6+ gxf6 18.g3 Re6 19.h4 Rae8 20.Rxe6 Qxe6 21.h5 \n" +
                                "Nf8 22.Kg2 Kg7 23.Rh1 h6 24.Rh4 Nh7 25.Bf5 Qe2 26.Bxh7 Qxf3+ 27.Kxf3 Kxh7 \n" +
                                "28.Rh1 Kg7 29.a3 f5 30.Rg1 Kf6 31.Rh1 Kg5 32.a4 Re4 33.a5 Re7 34.b3 Re4 \n" +
                                "35.b4 a6 36.Rg1 Kxh5 37.Rh1+ Kg5 38.Rg1 h5 39.Rh1 f4 40.Rg1 fxg3 41.fxg3 \n" +
                                "f5 42.Rh1 f4 43.gxf4+ Rxf4+ 44.Kg2 h4 45.Re1 Re4 46.Rf1 Re2+ 47.Kg1 Re7 \n" +
                                "48.Rf8 Kg4 49.Rg8+ Kf3 50.Rh8 Rg7+ 51.Kh2 Ke3 52.Rxh4 Kd3 53.Rh3+ Kc4 54.\n" +
                                "Re3 Rg4 55.Rf3 Re4 56.Kg2 Re2+ 57.Kf1 Rc2 58.Rf7 Rxc3 59.Rxb7 Kxd4 60.Rb6 \n" +
                                "Ke3 61.Rxa6 d4 62.Ra8 Rc1+ 63.Kg2 d3 64.Re8+ Kd4 65.Rd8+ Kc3 66.b5 cxb5 \n" +
                                "67.Rc8+ Kb2 68.Rxc1 Kxc1 69.a6 d2 70.a7 d1=Q 71.a8=Q Qd2+ 72.Kf3 b4 73.\n" +
                                "Qa1+ Kc2 74.Qa2+ Kc3 75.Qa1+ Kb3 76.Qb1+ Ka4 77.Qa1+ Kb5 78.Qe5+ Kc4 79.\n" +
                                "Qc7+ Kb3 80.Qf7+ Ka3 81.Qe7 Kb2 82.Kg4 Qd4+ 83.Kh5 b3 84.Qe2+ Kc3 85.Qe1+ \n" +
                                "Kd3 86.Qf1+ Kc2 87.Qg2+ Kc3 88.Qf3+ Kc4 89.Qf1+ Kb4 90.Qe1+ Ka4 91.Qe8+ \n" +
                                "Ka3 92.Qa8+ Kb4 93.Qb7+ Kc4 94.Qa6+ Kc3 95.Qc6+ Qc4 96.Qf6+ Kb4 97.Kh6 \n" +
                                "Qc1+ 98.Kh7 Qc2+ 99.Kh6 b2 100.Qb6+ Ka3 101.Qd6+ Ka2 102.Qa6+ Kb1 103.Kg7 \n" +
                                "Qg2+ 104.Kh8 Qh3+ 105.Kg8 Qg4+ 106.Kh8 Qd4+ 107.Kh7 Qe4+ 108.Kh6 Kc2 109.\n" +
                                "Qc8+ Kd1 110.Qd8+ Ke2",
                        "3Q4/8/7K/8/4q3/8/1p2k3/8 w - - 22 111"
                }
        };
    }

    @Test(dataProvider = "completeGames")
    public void testPgnLines(String pgnLine, String finalPos) {
        List<String> pgnMoves = MoveFormat.getMovesFromPgnLine(pgnLine);
        Board board = new Board(Position.STARTING_POSITION);

        for (String pgnMove : pgnMoves) {
            String move = MoveFormat.PGN.formatToAlgebraic(pgnMove, board);
            assertEquals(MoveFormat.PGN.formatFromAlgebraic(move, board), pgnMove);

            board.move(move);
        }

        assertEquals(board.getFEN(), finalPos);
    }
}
