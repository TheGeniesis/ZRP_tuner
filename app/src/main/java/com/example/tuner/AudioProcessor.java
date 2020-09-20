package com.example.tuner;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;

public class AudioProcessor implements Runnable {

    private static final String TAG = AudioProcessor.class.getCanonicalName();

    private static final int[] SAMPLE_RATES = {44100, 22050, 16000, 11025, 8000};

    public interface PitchDetectionListener {
        void onPitchDetected(float freq, double avgIntensity);
    }

    private float mLastComputedFreq = 0;

    private AudioRecord mAudioRecord;
    private PitchDetectionListener mPitchDetectionListener;
    private boolean stopRecording = false;
    private MainActivity mainActivity;

    public void setPitchDetectionListener(PitchDetectionListener pitchDetectionListener) {
        mPitchDetectionListener = pitchDetectionListener;
    }

    public void init() {
        // Recording initialization
        int bufSize = 16384;
        int availableSampleRates = SAMPLE_RATES.length;
        int i = 0;
        // do while audio recorder not initialized
        do {
            int sampleRate = SAMPLE_RATES[i];
            // set buffor size
            int minBufSize = AudioRecord.getMinBufferSize(sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT);
            if (minBufSize != AudioRecord.ERROR_BAD_VALUE && minBufSize != AudioRecord.ERROR) {
                mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, sampleRate, AudioFormat.CHANNEL_IN_MONO, AudioFormat.ENCODING_PCM_16BIT, Math.max(bufSize, minBufSize * 4));
            }
            i++;
        }
        while (i < availableSampleRates && (mAudioRecord == null || mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED));
    }

    public void stop() {
        stopRecording = true;
        mAudioRecord.stop();
        mAudioRecord.release();
    }

    @Override
    public void run() {
        mAudioRecord.startRecording();
        int bufSize = 8192;
        final int sampleRate = mAudioRecord.getSampleRate();
        final short[] buffer = new short[bufSize];

        do {
            final int read = mAudioRecord.read(buffer, 0, bufSize);

            // if the sound exists
            if (read > 0) {
                final double intensity = averageIntensity(buffer, read);

                int maxZeroCrossing = (int) (250 * (read / 8192) * (sampleRate / 44100.0));

                if (intensity >= 50 && zeroCrossingCount(buffer) <= maxZeroCrossing) {

                    // calculate frequency from sound array
                    float freq = getPitch(buffer, read / 4, read, sampleRate, 50, 500);

                    // set frequency value to TextView on main screen
                    this.mainActivity.setFrequencyValue(String.valueOf(freq));

                    // if sound is much louder than moment ago call method implemented in MainActivity
                    if (Math.abs(freq - mLastComputedFreq) <= 5f) {
                        mPitchDetectionListener.onPitchDetected(freq, intensity);
                    }
                    // update last frequency value
                    mLastComputedFreq = freq;
                }
            }
        } while (!stopRecording);
    }

    // calculate average intensity
    private double averageIntensity(short[] data, int frames) {

        double sum = 0;
        for (int i = 0; i < frames; i++) {
            sum += Math.abs(data[i]);
        }
        return sum / frames;
    }

    private int zeroCrossingCount(short[] data) {
        int len = data.length;
        int count = 0;
        boolean prevValPositive = data[0] >= 0;
        for (int i = 1; i < len; i++) {
            boolean positive = data[i] >= 0;
            if (prevValPositive == !positive)
                count++;

            prevValPositive = positive;
        }
        return count;
    }

    // calculate music pitch to be able to calculate frequency
    // use math formula
    private float getPitch(short[] data, int windowSize, int frames, float sampleRate, float minFreq, float maxFreq) {

        float maxOffset = sampleRate / minFreq;
        float minOffset = sampleRate / maxFreq;

        int minSum = Integer.MAX_VALUE;
        int minSumLag = 0;
        int[] sums = new int[Math.round(maxOffset) + 2];

        for (int lag = (int) minOffset; lag <= maxOffset; lag++) {
            int sum = 0;
            for (int i = 0; i < windowSize; i++) {

                int oldIndex = i - lag;

                int sample = ((oldIndex < 0) ? data[frames + oldIndex] : data[oldIndex]);

                sum += Math.abs(sample - data[i]);
            }

            sums[lag] = sum;

            if (sum < minSum) {
                minSum = sum;
                minSumLag = lag;
            }
        }

		// quadratic interpolation
		float delta = (float) (sums[minSumLag + 1] - sums[minSumLag - 1]) / ((float)
			(2 * (2 * sums[minSumLag] - sums[minSumLag + 1] - sums[minSumLag - 1])));
		return sampleRate / (minSumLag + delta);
    }

    // set context to update TextView in MainActivity
    public void setContext(MainActivity context) {
        this.mainActivity = context;
    }
}
