%
% Sistema del secondo ordine
%
close all; clear all; clc;

%% parametri del sistema

k = 1.0;
b = 0.5;
M = 2;

mu = 1/k;
xi = b/(2*sqrt(k*M));
omegan = sqrt(k/M);

s = tf('s');
%% definizione del sistema
fprintf('Funzione di trasferimento:\n');
if 0
    % fdt
    G = mu*omegan^2/(s^2 + 2*xi*omegan*s + omegan^2)
else
    % spazio degli stati
    A = [0 1; -k/M, -b/M];
    B = [0; 1/M];
    C = [1 0];
    D = 0;
    G = ss(A,B,C,D);
    tf(G)
end

%%

% elenco dei poli
poles = pole(G);
fprintf('Poli del sistema:\n');
disp(poles);


% diagramma poli/zeri
figure;
pzmap(G);

% risposta al gradino
figure;
step(G);grid on;

% proprietà della risposta
epsilon = 5; % percentuale per trovare tempo di assestamento
sinfo = stepinfo(G,'SettlingTimeThreshold', epsilon/100);
settling_time = sinfo.SettlingTime;
percentage_overshoot = 100 * (sinfo.Peak - mu)/mu;
peak_time = sinfo.PeakTime;
fprintf('Il tempo di assestamento ad epsilon = %i%% è di %.2f secondi\n', epsilon, settling_time);
fprintf('La massima sovraelongazione percentuale è di %.2f%%\n', percentage_overshoot);
fprintf('Il tempo di massima sovraelongazione è di %.2f secondi\n', peak_time);
