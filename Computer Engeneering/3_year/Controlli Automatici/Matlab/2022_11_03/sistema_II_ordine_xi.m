%
% Sistema del secondo ordine - coefficiente di smorzamento (xi)
%
close all; clear all; clc;

% parametri del sistema
mu = 1;
omegan = 15;

s = tf('s');
TT = 0:0.01:2; % intervallo temporale

figure;
hold on; box on; zoom on; grid on;

% ciclo su valori di xi da 0.1 a 0.9
for xi=0.1:0.1:0.9

    % definizione del sistema
    G = mu*omegan^2/(s^2 + 2*xi*omegan*s + omegan^2);

    % risposta al gradino
    YY = step(G, TT);

    % plot
    plot(TT, YY, 'DisplayName', ['\xi = ' num2str(xi)], 'LineWidth', 1.3);
end

legend;