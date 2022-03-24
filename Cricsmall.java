import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.*;
import java.time.LocalDate;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Scanner;

public class Cricsmall
{
    static BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(System.in));
    static PreparedStatement stmt;
    static int Team_id;
    static String team1_name;
    static String team2_name;


    public static void main(String args[]) throws Exception {
        Cricsmall main=new Cricsmall();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/cricket","root","");
        System.out.println("-----Cric Heros-----");
        System.out.println("1.Signup\n2.Teams\n3.Create a match\n4.Exit");
        System.out.println("Enter the option.");
        int num =Integer.parseInt(bufferedReader.readLine());

        switch(num) {
            case 1:
                // TODO: Sign up the page;
                main.Signup(con);


            case 2:
                // TODO: Teams;
                Teams(con);
                break;

            case 3:
                // TODO: Create a Match;
                CreateAMatch(con);
                break;

            case 4:
                //TODO: Completed matches;
                System.exit(0);
                break;
            default:
                break;
        }


    }

    public static void Signup(Connection con) throws Exception
    {
        //Page signup in the Application;

        stmt = con.prepareStatement("insert into signup(name,email,password) values(?,?,?)");
        System.out.println("Sign up ");
        Scanner scan = new Scanner(System.in);
        String name ="",email ="",password ="";

        while(true)
        {

            if(name.length() == 0)
            {
                System.out.println("Name: ");
                name = bufferedReader.readLine().trim();
                continue;
            }

            if(!email.contains("@gmail.com"))
            {
                System.out.println("Email id: ");
                email = bufferedReader.readLine().trim();
                continue;
            }

            if((password.length() < 5))
            {
                System.out.println("Password: ");
                password = bufferedReader.readLine().trim();
                continue;
            }
            else
            {
                stmt.setString(1,name);
                stmt.setString(2,email);
                stmt.setString(3,password);
            }
            if(stmt.executeUpdate() == 1)
            {
                System.out.println("Data inserted successfully.");
            }
            else
            {
                System.out.println("Failed.");
            }
            System.exit(0);
        }

    }



    public static void Teams(Connection con) throws Exception
    {
        //Create two teams:

        stmt = con.prepareStatement("insert into team1(T_id,Team_name) values(?,?)");
        System.out.println("Create team:");
        Scanner scan = new Scanner(System.in);
        int T_id= 0;
        String Team_name = "";

        while(true)
        {
            // First team creating:

            System.out.println("Team id: ");
            T_id = Integer.parseInt(bufferedReader.readLine().trim());
            stmt.setString(1, String.valueOf(T_id));

            System.out.println("Team name:");
            Team_name = bufferedReader.readLine().trim();
            stmt.setString(2, Team_name);

            if (stmt.executeUpdate() == 1) {
                System.out.println();
            } else {
                System.out.println("Try again.");
            }
            break;
        }


        while(true)
        {
            //Second team creating:

            stmt = con.prepareStatement("insert into team2(T_id,Team_name) values(?,?)");
            System.out.println("Opponent team:");

            System.out.println("Team id: ");
            T_id = Integer.parseInt(bufferedReader.readLine().trim());
            stmt.setString(1, String.valueOf(T_id));

            System.out.println("Team name:");
            Team_name = bufferedReader.readLine().trim();
            stmt.setString(2, Team_name);

            if (stmt.executeUpdate() == 1) {
                System.out.println("Teams are added.");

            } else {
                System.out.println("Try again.");

            }
            System.exit(0);
        }

    }


    public static void CreateAMatch(Connection con) throws Exception
    {
        //  First team players score:

        System.out.println("Create Matches: ");
        stmt = con.prepareStatement("select Team_name from team1 where T_id=? limit 1");
        stmt.setString(1, "1");
        ResultSet rs = stmt.executeQuery();
        String team1_name="";
        while(rs.next())
        {
            team1_name = rs.getString(1);

        }
        System.out.println(team1_name+" Batting.");
        stmt = con.prepareStatement("insert into teamA(team_name,jersey_no,players_name,score,balls) values(?,?,?,?,?)");
        stmt.setString(1,team1_name);
        Scanner scan = new Scanner(System.in);
        Date date1 = Date.valueOf(LocalDate.now());
        System.out.println(date1);

        int jersey_no = 0;
        String players_name = "";
        int score = 0;
        float balls = 0;
        int total_score1 = 0;
        int total1 = 0;
        float total_balls =0;
        float total = 0;


        for(int i=0;i<2;i++)
        {
            stmt.setString(1,team1_name);
            System.out.println("Player-jersey-no");
            jersey_no = Integer.parseInt(bufferedReader.readLine().trim());
            stmt.setInt(2,jersey_no);

            System.out.println("Player name");
            players_name = bufferedReader.readLine().trim();
            stmt.setString(3,players_name);

            System.out.println("Score:");
            score = Integer.parseInt(bufferedReader.readLine().trim());
            stmt.setString(4, String.valueOf(score));

            System.out.println("Take a balls:");
            balls = Integer.parseInt(bufferedReader.readLine().trim());
            stmt.setString(5, String.valueOf(balls));



            if(stmt.executeUpdate() == 1)
            {
                total_score1 += score;
                total_balls += balls;
                total = total_balls/6;
                System.out.println("Total_Score: "+ total_score1);
                System.out.println("Overs"+total);

            }
            else
            {
                System.out.println("Try-again");


            }
        }



        // A-Team score:

        stmt = con.prepareStatement("insert into score1(date,Team,total_score,overs) values(?,?,?,?)");
        String overs = "";
        int target = 1;

        while(true) {
            System.out.println(date1);
            stmt.setString(1, String.valueOf(date1));

            System.out.println(team1_name);
            stmt.setString(2, team1_name);

            System.out.println("team1_name score- "+ total_score1);
            stmt.setInt(3, Integer.parseInt(String.valueOf(total_score1)));


            System.out.println(total);
            stmt.setString(4, String.valueOf(total));

            if (stmt.executeUpdate() == 1) {
                target+=total_score1;
                System.out.println("TeamB Need to " + target + " Runs..");

            } else {
                System.out.println("Please try again.");
            }
            break;

        }



        // Second team players score:

        stmt = con.prepareStatement("select Team_name from team2 where T_id=? order by Team_name desc limit 1");
        stmt.setString(1, "2");
        ResultSet rs1 = stmt.executeQuery();
        String team2_name = "";
        while (rs1.next())
        {
            team2_name = rs1.getString(1);
        }
        System.out.println();
        System.out.println(team2_name);
        stmt = con.prepareStatement("insert into teamB(team_name,jersey_no,players_name,score,balls) values(?,?,?,?,?)");

        int jersey1_no = 0;
        String player_name = "";
        int score2 = 0;
        int total_score2 = 0;
        float balls2 = 0;
        int total2 = 0;
        float total_balls2 = 0;
        float total_overs = 0;

        while (true) {
            for (int i = 0; i < 2; i++) {
                stmt.setString(1, team2_name);

                System.out.println("Jersey-no");
                jersey_no = Integer.parseInt(bufferedReader.readLine().trim());
                stmt.setString(2, String.valueOf(jersey_no));

                System.out.println("Player name:");
                players_name = bufferedReader.readLine().trim();
                stmt.setString(3, players_name);

                System.out.println("Score:");
                score = Integer.parseInt(bufferedReader.readLine().trim());
                stmt.setString(4, String.valueOf(score));

                System.out.println("Balls");
                balls = Integer.parseInt(bufferedReader.readLine().trim());
                stmt.setString(5, String.valueOf(balls));

                if (stmt.executeUpdate() == 1) {
                    total_score2 += score;
                    System.out.println(total_score2);
                    total_balls += balls;
                    total_overs = total_balls / 6;
                    System.out.println(total_overs);
                } else {
                    System.out.println("try again.");
                }
            }


            break;
        }


        while (true) {

            // B-Team score:

            stmt = con.prepareStatement("insert into score2(date,team2,total_score,overs) values(?,?,?,?)");
            Date date = Date.valueOf(LocalDate.now());
            int total_score = 0;
            String overs2 = "";
            System.out.println(team2_name + " score. "+ total_score2);

            System.out.println(date);
            stmt.setDate(1, date);


            stmt.setString(2, team2_name);

            stmt.setString(3, String.valueOf(total_score2));

            System.out.println();
            stmt.setString(4, String.valueOf(total_overs));

            if (stmt.executeUpdate() == 1) {

                System.out.println("");
            } else {
                System.out.println("try again");
            }
            break;
        }

        // Total score for teams and Match result:

        System.out.println("Score Board");
        stmt = con.prepareStatement("insert into scoreBoard(match_no,date,teamA,teamA_score,teamB,teamB_score,winning_team,win_by) values(?,?,?,?,?,?,?,?)");

        int match = 0;
        int num = 1;
        Date date = Date.valueOf(LocalDate.now());
        int teamB_score = 0;
        String winning_team = "";
        int win_by = 0;
        String win_score = "";


        while(true) {


            stmt.setString(1, String.valueOf(match+num));

            stmt.setString(2, String.valueOf(date));

            System.out.println("Team A:" + team1_name);
            stmt.setString(3, team1_name);

            System.out.println("Team A score:" + total_score1);
            stmt.setInt(4, total_score1);

            System.out.println("Team B: " + team2_name);
            stmt.setString(5, team2_name);

            System.out.println("Team B score:" + total_score2);
            stmt.setInt(6, total_score2);


            if (total_score1 > total_score2) {

                stmt.setString(7, team1_name);
            } else if (total_score2 > total_score1) {

                stmt.setString(7, team2_name);
            } else if (total_score1 == total_score2) {
                stmt.setString(7, "Match_die");
            }

            if (total_score1 > total_score2) {
                win_score = String.valueOf(total_score1 - total_score2);
                System.out.println(team1_name + " win by " + win_score + "runs.");
                stmt.setInt(8, Integer.parseInt(win_score));
            } else if (total_score2 > total_score1) {
                win_score = String.valueOf(total_score2 - total_score1);
                System.out.println(team2_name + " win by " + win_score + " runs.");
                stmt.setInt(8, Integer.parseInt(win_score));
            } else if (total_score1 == total_score2) {
                System.out.println("Match_die.");
                stmt.setString(8, "Match_die");
            }


            if (stmt.executeUpdate() == 1) {
                System.out.println("Match completed.");

                // Delete from tables:

                stmt = con.prepareStatement("delete from team1");
                stmt.executeUpdate();
                stmt = con.prepareStatement("delete from team2");
                stmt.executeUpdate();
                stmt = con.prepareStatement("delete from teamA");
                stmt.executeUpdate();
                stmt = con.prepareStatement("delete from teamB");
                stmt.executeUpdate();
                stmt = con.prepareStatement("delete from score1");
                stmt.executeUpdate();
                stmt = con.prepareStatement("delete from score2");
                stmt.executeUpdate();
            } else {
                System.out.println("Try again.");
            }

            System.exit(0);

        }

    }
}
