%
% Sistema del primo ordine
%
close all; clear all; clc;

% parametri del sistema
mu = 1;
T = 1;
epsilon = 5; % percentuale per trovare tempo di assestamento

% definizione del sistema
fprintf('Funzione di trasferimento:\n');
if 1
    % fdt
    s = tf('s');
    G = mu/(1+T*s)
else
    % spazio degli stati
    A = -1/T;
    B = mu/T;
    C = 1;
    D = 0;
    G = ss(A,B,C,D);
    tf(G)
end

% elenco dei poli
poles = pole(G);
fprintf('Poli del sistema:\n');
disp(poles);

pause;

% diagramma poli/zeri
figure;
pzmap(G);

% risposta al gradino
figure;
step(G);

% tempo di assestamento
sinfo = stepinfo(G,'SettlingTimeThreshold', epsilon/100);
settling_time = sinfo.SettlingTime;
fprintf('Il tempo di assestamento ad epsilon = %i%% è di %.1f secondi\n', epsilon, settling_time);
