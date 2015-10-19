% plotfft.m
% Author: Tim Woodford

%% Script parameters
bucketrange=1:1024;
fname='sinefft.csv';

%% Import/process data
[freq, real, imag] = importfft(fname);
magnitude = sqrt(real.*real + imag.*imag);

%% Plot data
plot(freq(bucketrange), magnitude(bucketrange));
xlabel('Frequency (Hz)');
ylabel('Magnitude of the Amplitude');