package com.iskandar.android.stopwatch;


public class TimeDisplay {

    protected int sec;
    protected int min;
    protected int hour;


    private TimeDisplay(int sec, int min, int hour)
    {
        this.sec = sec;
        this.min = min;
        this.hour = hour;
    }

    public TimeDisplay() {
        this(0,0,0);
    }

    public TimeDisplay(TimeDisplay other)
    {
        this(other.sec,other.min,other.hour);
    }

    public void tick()
    {
        this.sec+=1; // tick tock tick tock tick tock
        if(this.sec>59)
        {
            this.sec=0;
            this.min+=1;
            if(this.min>59)
            {
                this.min=0;
                this.hour+=1;
            }
        }
    }

    @Override
    public String toString() {
        return (hour<10?"0":"")+hour+":"+
                (min<10?"0":"")+min+":"+
                (sec<10?"0":"")+sec;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TimeDisplay that = (TimeDisplay) o;
        return sec == that.sec &&
                min == that.min &&
                hour == that.hour;
    }

}