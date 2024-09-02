% Esempi calcolo funzioni di sensitività
% 
% Controlli Automatici T
% 2022/23
%

clear all, close all, clc


s = tf('s');

% definizione sistema in anello aperto
mu = 40;
TT = 10;
TT2 = 2;
TT3 = 0.2;

RR = mu; % controllore
GG = 1/(1 + TT*s)/(1 + TT2*s)/(1 + TT3*s); % sistema
LL = RR * GG; % funzione d'anello
LL.OutputName = 'output';
LL.InputName  = 'error';
LL

% margini di stabilità
[M_a,M_f,omega_pi,omega_c] = margin(LL)
M_a_db = 20*log10(M_a)

%% FUNZIONE DI SENSITIVITÀ COMPLEMENTARE

% ci sono 3 alternative possibili per calcolare la F(s)

% alternativa 1: interconnessione dei blocchi
Sum = sumblk('error = ref - output'); % nodo sommatore
FF_connect = connect(LL, Sum, 'ref', 'output'); % sistema interconnesso
FF_connect = tf(minreal(FF_connect))
FF_connect_zpk = zpk(FF_connect)

% alternativa 2: funzione "feedback"
FF_feedback = feedback(LL, 1);
FF_feedback.OutputName = 'output';
FF_feedback.InputName  = 'ref';
FF_feedback = tf(minreal(FF_feedback))

% alternativa 3: usare espressione F = L/(1+L)
FF = LL / (1 + LL);
FF.OutputName = 'output';
FF.InputName  = 'ref';
FF = tf(minreal(FF))

%% FUNZIONE DI SENSITIVITÀ

% anche qui ci sono più alternative

% alternativa 1: interconnessione dei blocchi
Sum1 = sumblk('error = ref - noisyoutput');    % nodo sommatore (sx)
Sum2 = sumblk('noisyoutput = output + noise'); % nodo sommatore (dx)
SS_connect = connect(LL, Sum1, Sum2, 'noise', 'noisyoutput'); % sistema interconnesso
SS_connect = tf(minreal(SS_connect))

% alternativa 2: usare espressione S = 1/(1+L)
SS = 1 / (1 + LL);
SS.OutputName = 'noisyoutput';
SS.InputName  = 'noise';
SS = tf(minreal(SS))

%% BODE PLOT

figure(1)
bode(LL)
grid on, zoom on


figure(2)
bode(FF)
grid on, zoom on


figure(3)
bode(SS)
grid on, zoom on