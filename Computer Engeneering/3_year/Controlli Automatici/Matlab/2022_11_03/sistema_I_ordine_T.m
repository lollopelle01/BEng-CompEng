%
% Sistema del primo ordine
%
close all; clear all; clc;

mu = 1; epsilon = 5;
s = tf('s');

figure;
hold on; box on; zoom on; grid on;
TT = 0:0.1:15;

for T=0.1:0.5:3.1

    % definizione del sistema
    G = mu/(1+T*s);

    % risposta al gradino
    YY = step(G, TT);
    % tempo di assestamento
    sinfo = stepinfo(G,'SettlingTimeThreshold', epsilon/100);
    settling_time = sinfo.SettlingTime;
    fprintf('T = %.1f, Ta,5 = %.1f secondi\n', T, settling_time);

    % plot
    plot(TT, YY, 'DisplayName', ['T = ' num2str(T)], 'LineWidth', 1.3);
    ylim([-0.1 1.1])
    
    
end

legend;