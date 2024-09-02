clear all; close all; clc;

mu = 1;
T1 = 1;
T2 = 3;
s = tf('s');


TT = 0:0.01:15; % intervallo temporale

figure;
hold on; box on; zoom on; grid on;

% ciclo su valori di tau
for tau=[0, 1, 2, 5]
    
    % definizione del sistema
    G = mu*(1+tau*s)/((1+T1*s)*(1+T2*s));
    % risposta al gradino
    YY = step(G, TT);

    % plot
    plot(TT, YY,'DisplayName', ['\tau = ' num2str(tau)], 'LineWidth', 1.3);
end
legend;