
package mfec;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import static java.lang.Integer.parseInt;
import static java.lang.System.in;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class MFEC {

    public static void main(String[] args) {
            
        String[] fullName = new String[50];
        String time1 = null, time2 = null, time3 = null, time4 = null;
        Date date1, date2;
        long totalTime, OtTimeTotal,lateTimeTotal, totalTimeHH = 0, totalTimeMM = 0;
        long totalOtTimeMM = 0, totalOtTimeHH = 0;
        long lateTimeHH = 0, lateTimeMM = 0;
        int[] OtTime = new int[50];
        int timeCheck1 = 0, timeCheck2 = 0,timeCheck3 = 0, timeCheck4 = 0;
        int timeStartCheck1 = 0,timeStartCheck2 = 0,timeStartCheck3 = 0;
        Calendar c = Calendar.getInstance();
        
        
        
            
        try{
            FileInputStream fstream = new FileInputStream("working_time.log");
            BufferedReader br = new BufferedReader(new InputStreamReader(fstream));
            String strLine;
            int i = 0,num = 0;
            Date d1 = null;
            Date d2 = null;
            Date d3 = null;
            Date d4 = null;
            Date d5 = null;
            System.out.println("--------------------------------------------------------------");
            
            while ((strLine = br.readLine()) != null)   {
              String[] array = strLine.split("\\|", -5);
              String[] inTime = array[2].split("\\:");
              String[] outTime = array[4].split("\\:");
              String[] dateStart = array[1].split("\\/");
              int YYStart = parseInt(dateStart[2]);
              int MMStart = parseInt(dateStart[1]);
              int DDStart = parseInt(dateStart[0]);
              
              if(MMStart == 1){
                  c.set(YYStart, Calendar.JANUARY, DDStart);
              }else if (MMStart == 2){
                  c.set(YYStart, Calendar.FEBRUARY, DDStart);
              }else if (MMStart == 3){
                  c.set(YYStart, Calendar.MARCH, DDStart);
              }else if (MMStart == 4){
                  c.set(YYStart, Calendar.APRIL, DDStart);
              }else if (MMStart == 5){
                  c.set(YYStart, Calendar.MAY, DDStart);
              }else if (MMStart == 6){
                  c.set(YYStart, Calendar.JUNE, DDStart);
              }else if (MMStart == 7){
                  c.set(YYStart, Calendar.JULY, DDStart);
              }else if (MMStart == 8){
                  c.set(YYStart, Calendar.AUGUST, DDStart);
              }else if (MMStart == 9){
                  c.set(YYStart, Calendar.SEPTEMBER, DDStart);
              }else if (MMStart == 10){
                  c.set(YYStart, Calendar.OCTOBER, DDStart);
              }else if (MMStart == 11){
                  c.set(YYStart, Calendar.NOVEMBER, DDStart);
              }else {
                  c.set(YYStart, Calendar.DECEMBER, DDStart);
              }
              
              
              
          
              try{
                      fullName[i] = array[0];
                      SimpleDateFormat sdf = new SimpleDateFormat("dd/M/yyyy HH:mm");
                      if(!"".equals(inTime[0])){
                          
                        timeCheck1 = parseInt(outTime[0]);
                        timeCheck2 = parseInt(outTime[1]);
                        
                      //-------------------------Total Normal Time------------------------------//
                        if(timeCheck1 >= 17){
                            if(timeCheck1 == 17 && timeCheck2 >= 30){
                               time1 = array[1] +" " +inTime[0]+":"+inTime[1];
                               time2 = array[3] + " 17:30";
                            }else if (timeCheck1 > 17){
                               time1 = array[1] +" " +inTime[0]+":"+inTime[1];
                               time2 = array[3] + " 17:30";   
                            }else{
                               time1 = array[1] +" " +inTime[0]+":"+inTime[1];
                               time2 = array[3] + " " +outTime[0]+":"+outTime[1]; 
                            }
                        }else{
                            time1 = array[1] + " " +inTime[0]+":"+inTime[1];
                            time2 = array[3] + " " +outTime[0]+":"+outTime[1];
                        }
                        //----------------------------------------------------------------------//
                        
                        
                        timeStartCheck1 = parseInt(inTime[0]);
                        timeStartCheck2 = parseInt(inTime[1]);
   
                        if(timeStartCheck1 <= 8 && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                            if(timeStartCheck1 == 8 && timeStartCheck2 == 0){
                              time1 = array[1] +" 8:00";  
                            }else if(timeStartCheck1 < 8){
                              time1 = array[1] +" 8:00";   
                            }
                        }
                        
                        
                        
                        
                        
                        
                        //-----------------------Total Normal OT Time---------------------------//
                        int check = 0;
                        try{
                            check = parseInt(inTime[0]);
                            
                        } catch (Exception e){}
                        if(check > 17){
                         time3 = array[1] + " " + inTime[0] + ":"+inTime[1];
                         
                        } else {
                            time3 = array[1] + " 17:30";
                        }
                        
                        time4 = array[3] + " " + outTime[0]+":"+outTime[1];
                        //----------------------------------------------------------------------//
                        
                        String time5 = array[1]+" 8:5";
                          
                        
                        d1 = sdf.parse(time1);
                        d2 = sdf.parse(time2);
                        d3 = sdf.parse(time3);
                        d4 = sdf.parse(time4);
                        d5 = sdf.parse(time5);
                        OtTimeTotal = d4.getTime() - d3.getTime();
                        totalTime = d2.getTime() - d1.getTime();
                        lateTimeTotal = d1.getTime() - d5.getTime();
                        totalOtTimeMM = OtTimeTotal / (60 * 1000) % 60;
                        totalOtTimeHH = OtTimeTotal / (60 * 60 * 1000);
                        totalTimeMM = totalTime / (60 * 1000) % 60;
                        totalTimeHH = totalTime / (60 * 60 * 1000);
                        lateTimeHH = lateTimeTotal / (60 * 60 * 1000);
                        lateTimeMM = lateTimeTotal / (60 * 1000) % 60;
                        
                        if(check > 17 && (totalOtTimeHH > 0 || totalOtTimeMM > 0)){
                            totalTimeHH = 1;
                            totalTimeMM = 0;
                        }
                          
                      }else{
                        //---------------------Not Time Case----------------------------------//
                        time1 = array[1] +" 00:00";
                        time2 = array[3] +" 00:00";
                        d1 = sdf.parse(time1);
                        d2 = sdf.parse(time2);
                        totalTime = d2.getTime() - d1.getTime();
                        totalTimeMM = totalTime / (60 * 1000) % 60;
                        totalTimeHH = totalTime / (60 * 60 * 1000);
                        //----------------------------------------------------------------------//
                      }
                      
              } catch (Exception  e){}
                
              try{
                timeCheck3 = parseInt(inTime[0]);
                timeCheck4 = parseInt(inTime[1]);
                } catch (Exception e){}
                
                System.out.println(i+1+". "+fullName[i]);
                
                if(c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                    long timeHH = totalTimeHH;
                    timeHH--;
                    try{
                        timeStartCheck3 = parseInt(outTime[0]);
                    
                    }catch(Exception e){}
                    
                    System.out.println("Working normal time hours   : "+timeHH+" hours");
                    System.out.println("Working normal time minutes : "+totalTimeMM+" minutes");
                }
                
             
                
                if(timeCheck3 <= 12 && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                    
                        double total = calNormaltime(totalTimeHH, totalTimeMM, timeCheck1);
                        System.out.printf("Total  :  %.2f ฿.\n",total);
                    
                
                }
                
                if((totalOtTimeHH > 0 || totalOtTimeMM > 0) && c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
                   double otTotal = calOtNormaltime(totalOtTimeHH,totalOtTimeMM);
                   System.out.println("Ot time ==> "+"HH: "+totalOtTimeHH+" h"+"  MM: "+totalOtTimeMM+" m");
                   System.out.printf("Total OT :  %.2f ฿.\n",otTotal);
                }
                
                if((c.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY && c.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY)&&lateTimeHH <= 4 && lateTimeMM > 0){
                    double late = calLate(lateTimeHH, lateTimeMM);
                    System.out.println("Late Time!!! : "+ lateTimeHH+" h  "+lateTimeMM+" m  =  -"+late + " ฿.");
                }
                try{
                timeStartCheck3 = parseInt(outTime[0]);
                    
                }catch(Exception e){}
                
                if((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY)){
                    
                    double totalHoliday = calHoliday(totalTimeHH, totalTimeMM);
                    long timeHH;
                    timeHH = totalTimeHH;
                    if(timeHH > 5){
                    timeHH--;
                    }
                    if(!(timeStartCheck3 <= 18 && timeStartCheck3 >= 7)){
                        timeHH = 0;
                        totalTimeMM = 0;
                        totalHoliday = 0;
                    }
                        System.out.println("Working holiday time hours   : " + timeHH + " hours");
                        System.out.println("Working holiday time minutes : " + totalTimeMM + " minutes");
                        System.out.printf("Total holiday times : %.2f ฿.\n",totalHoliday);
                    
                }
                
                
                if((c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) && totalOtTimeMM > 0 ){
                    
                    double totalOtHoliday = calOtHoliday(totalOtTimeHH,totalOtTimeMM);
                    
                    System.out.println("Working holiday ot time ==> HH "+totalOtTimeHH+" h MM "+totalOtTimeMM+" m");
                    System.out.printf("Total ot holiday : %.2f ฿. \n",totalOtHoliday);
                    
                }
            
            
            System.out.println("---------------------------------------------------------------");
            i++;
            totalOtTimeMM = 0;
            totalOtTimeHH = 0;
            }
            in.close();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        
    }
    
    
    public static double calNormaltime(long HH, long MM, int timeCheck){
        if(timeCheck > 12){
            HH = HH - 1;
        }
        double sum;
        sum = HH*36.25;
        sum = sum + (MM * 0.6041667);
        if(sum<0){
         sum = 0;   
        }
        return(sum);
    }
    
    
    public static double calOtNormaltime(long OtHH, long OtMM){
        double sum;
        sum = OtHH * 36.25 * 1.5;
        sum = sum + (OtMM * 0.6041667 * 1.5);
        if(sum < 0){
            sum = 0;
        }
        return(sum);
    }
    
    public static double calLate(long lateHH, long lateMM){
        double sum;
        sum = lateHH * 36.25;
        sum = sum + (lateMM * 0.6041667);
        return(sum);
    }
    
    public static double calHoliday(long HH, long MM){
        double sum;
        if(HH > 5){
            HH = HH - 1;
        }
        sum = HH * 36.25 * 1.5;
        sum = sum + (MM * 0.6041667 * 1.5);
        return(sum);
    }
    
    public static double calOtHoliday(long HotHH, long HotMM){
        
        double sum;
        sum = HotHH * 36.25 * 2;
        sum = sum + (HotMM * 0.6041667 * 2);
        return(sum);
    }
}