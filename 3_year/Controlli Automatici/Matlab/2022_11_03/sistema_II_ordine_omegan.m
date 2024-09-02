%
% Sistema del secondo ordine - coefficiente di smorzamento (xi)
%
close all; clear all; clc;

% parametri del sistema
mu = 1;
xi = 0.7;

s = tf('s');
TT = 0:0.01:15; % intervallo temporale

figure;
hold on; box on; zoom on; grid on;

% ciclo su valori di omegan da 0.5 a 3
for omegan=0.5:0.5:3
    
    % definizione del sistema
    G = mu*omegan^2/(s^2+2*xi*omegan*s+omegan^2);
     stepinfo(G).Overshoot
    % risposta al gradino
    YY = step(G, TT);

    % plot
    plot(TT, YY,'DisplayName', ['\omega_n = ' num2str(omegan)], 'LineWidth', 1.3);
end
legend;