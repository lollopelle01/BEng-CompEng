clear all; close all; clc;

mu = 1;
T1 = 10;
T2 = 1;
s = tf('s');


TT = 0:0.01:25; % intervallo temporale

figure;
hold on; box on; zoom on; grid on;

% ciclo su valori di tau
for tau=[T1-2, T1, T1+2]
    
    % definizione del sistema
    G = mu*(1+tau*s)/((1+T1*s)*(1+T2*s));
    % risposta al gradino
    YY = step(G, TT);

    % plot
    plot(TT, YY, 'LineWidth', 1.3);
end
legend('\tau < T_1','\tau = T_1','\tau > T_1');