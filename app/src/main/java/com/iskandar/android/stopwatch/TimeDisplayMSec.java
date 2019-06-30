package com.iskandar.android.stopwatch;


public class TimeDisplayMSec extends TimeDisplay {


    public static final int MSEC_FULL_TICK = 1000;
    public static final int MSEC_HALF_TICK = 500;
    public static final int MSEC_QUARTER_TICK = 250;
    public static final int MSEC_FIFTH_TICK = 200;
    public static final int MSEC_EIGHTH_TICK = 125;
    public static final int MSEC_TENTH_TICK = 100;


    private int msec;
    private int tickValue;

    public TimeDisplayMSec(int tickValue) {
        super();
        this.msec=0;
        this.tickValue=tickValue;
    }

    public TimeDisplayMSec(TimeDisplayMSec other)
    {
        super(other);
        this.msec = other.msec;
        this.tickValue=other.tickValue;
    }

    @Override
    public void tick() {

        this.msec+=this.tickValue;
        if(this.msec>999) {
            this.msec = 0;
            this.sec += 1;
            if (this.sec > 59) {
                this.sec = 0;
                this.min += 1;
                if (this.min > 59) {
                    this.min = 0;
                    this.hour += 1;
                }
            }
        }
    }

    @Override
    public String toString() {
        return super.toString()+
                "."+(msec<100?(msec<10?"00":"0"):"")+msec;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        TimeDisplayMSec that = (TimeDisplayMSec) o;
        return msec == that.msec;
    }

}
