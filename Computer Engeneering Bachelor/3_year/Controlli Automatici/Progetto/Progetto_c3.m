clear all; close all; clc;

% Specifiche:
WW = pi/6;          % --> ampiezza gradino in ingresso w(t)=WW*1(t)

DD = pi/6;          % --> ampiezza gradino rumore d(t)=DD*1(t)

e_star = 0.01;      % --> errore a regime

Abbattimento_d = 50;    % |--> attenuazione disturbo sull'uscita
omega_d_max = 0.1;      % |
omega_d_min = 0.0001;   % |

Abbattimento_n = 35;    % |--> attenuazione disturbo di misura
omega_n_max = 1e6;      % |
omega_n_min = 1e3;      % |

S_100_spec = 0.05;  % --> sovraelongazione massima
T_a5_spec = 0.075;  % --> tempo di assestamento
T_a5_spec_val = 0.05;  % --> Margine massimo oltre tempo di assestamento

% Parametri fisici del sistema
m_i = 5;
e_i = 0.1;
I_e = 50;
b = 50;
g = 9.8;
theta_e = pi/6; 

s = tf('s');

%% PRIMO PUNTO

% Equilibrio
x2_equilibrio = 0;
x1_equilibrio = theta_e;
xe = [x1_equilibrio; x2_equilibrio];
tau_equilibrio = 1/2*g*m_i*e_i;

% Matrici linearizzate
A = [0 1; (-g*m_i*e_i*cos(theta_e))/(m_i*(e_i)^2 + I_e) (-b)/(m_i*(e_i)^2 + I_e)];
B = [0; 1/(m_i*(e_i)^2 + I_e)];
C = [1 0];
D = 0;

% Linearizzazione del sistema
sistema_linearizzato = ss(A, B, C, D);

%% SECONDO PUNTO

% Funzione di trasferimento
G = tf(sistema_linearizzato);
G = zpk(G); % --> G(s) fattorizzata

% Elenco dei poli --> 2 poli reali < 0 --> necessario per BIBOstabilità
poles = pole(G);
fprintf('Poli del sistema:\n');
disp(poles);

% Diagramma di bode (senza patch)
figure(1);
bode(G);
grid on; zoom on;

% Diagramma zeri/poli
figure(2);
pzmap(G);
grid on; zoom on;

%% TERZO PUNTO  (Situazione di partenza)

% S_star <= e^((-pi*xi^*)/(sqrt(a-(xi^*)^2)))
logpisquared = (-(1/pi)*log(S_100_spec))^2;
xi_star = sqrt(logpisquared/(1+logpisquared)); % perché Mf_spec > 45° da cui xi_star > 0
Mf_spec = xi_star*100;

% Ta,5% = 3/(xi_star*omega_c_min) = 300/(Mf_spec*T_a5_spec)
omega_Ta_max = 300/(Mf_spec*T_a5_spec);

figure(3);
hold on;

% Patch per disturbo d(t) --> disturbo in uscita
Bnd_d_x = [omega_d_min; omega_d_max; omega_d_max; omega_d_min];
Bnd_d_y = [Abbattimento_d; Abbattimento_d; 0; 0]; 
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);

% Patch per disturbo n(t) --> rumore di misura
Bnd_n_x = [omega_n_min; omega_n_max; omega_n_max; omega_n_min];
Bnd_n_y = [-Abbattimento_n; -Abbattimento_n; 100; 100];
patch(Bnd_n_x, Bnd_n_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);

% Patch per tempo di assestamento
omega_Ta_min = 1e-4;
Bnd_Ta_x = [omega_Ta_min; omega_Ta_max; omega_Ta_max; omega_Ta_min];
Bnd_Ta_y = [0; 0; -150; -150];
patch(Bnd_Ta_x, Bnd_Ta_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);
% Legenda colori
Legend_mag = ["d(t)"; "n(t)"; "T_a_,_5_%"];
legend(Legend_mag);

%Diagramma di bode con patch
margin(G);
grid on; zoom on; 

% Patch per il margine di fase
omega_c_min = omega_Ta_max;
omega_c_max = omega_n_min;
phi_spec = Mf_spec - 180;
phi_low = -270;
Bnd_Mf_x = [omega_c_min; omega_c_max; omega_c_max; omega_c_min];
Bnd_Mf_y = [phi_spec; phi_spec; phi_low; phi_low];
patch(Bnd_Mf_x, Bnd_Mf_y,'y','FaceAlpha',0.2,'EdgeAlpha',0);

% Legenda colori
Legend_arg = ["G(j\omega)"; "M_f"];
legend(Legend_arg);


%% TERZO PUNTO  (Regolatore statico e dinamico)
% Progettazione regolagore statico
figure(4);
hold on;

% Patch per disturbo d(t) --> disturbo in uscita
patch(Bnd_d_x, Bnd_d_y,'r','FaceAlpha',0.2,'EdgeAlpha',0);

% Patch per disturbo n(t) --> rumore di misura
patch(Bnd_n_x, Bnd_n_y,'g','FaceAlpha',0.2,'EdgeAlpha',0);

% Patch per margine di fase
patch(Bnd_Ta_x, Bnd_Ta_y,'b','FaceAlpha',0.2,'EdgeAlpha',0);

% REGOLATORE STATICO
G_0 = abs(evalfr(G, 0));
mu_s = ((DD+WW)/e_star); % specifica per guadagno d'anello 
factor = 15; %fattore aggiuntivo per risoluzione del vincolo sull'abbattimento di d(t)
RR_s = factor*mu_s/G_0;
G_e = G*RR_s;

% REGOLATORE DINAMICO
Mf_star = Mf_spec + 5;
omega_c_star = 60;
[mag_omega_c_star, arg_omega_c_star, ~] = bode(G_e, omega_c_star);
mag_omega_c_star_db = 20*log10(mag_omega_c_star);
M_star = 10^(-mag_omega_c_star_db/20); % dalla teoria: |G_e(wc_star)|dB + 20log10(M_star) = 0 
phi_star = Mf_star - 180 - arg_omega_c_star; % dalla teoria: Mf_star = 180° + arg{G_e(wc_star)} + fi_star
at = (cos(phi_star*pi/180)-1/M_star)/(sin(phi_star*pi/180)*omega_c_star);   %|
t = (M_star-cos(phi_star*pi/180))/(sin(phi_star*pi/180)*omega_c_star);      %|--> Da formule di inversione
RR_d = (1+t*s)/(1+at*s);
L = G_e*RR_d;
legend(Legend_mag);

Regolatore_totale = RR_s*RR_d;

% stampa risultato
margin(L);
%margin(G_e)    | --> per visualizzare il diagramma di Bode della sola funzione estesa
grid on;zoom on;

% Patch per la sovraelongazione
patch(Bnd_Mf_x, Bnd_Mf_y,'y','FaceAlpha',0.2,'EdgeAlpha',0);

% Legenda colori
legend(Legend_arg);

%% QUARTO PUNTO

% w(t)
W_test = 20;
T_simulation = 3;

% d(t) e n(t)
time_d = (0:1e-2:1000); % perchè periodo max è 251s
D_test = 0.1*(  sin(0.025*1*time_d) + ...
                sin(0.025*2*time_d) + ...
                sin(0.025*3*time_d) + ...
                sin(0.025*4*time_d));
time_n = (0:1e-6:0.1); % perchè periodo max è 0.0063s
N_test = 0.6*(  sin(1e3*1*time_n) + ...
                sin(1e3*2*time_n) + ...
                sin(1e3*3*time_n) + ...
                sin(1e3*4*time_n));

% Funzioni sensitività
F = minreal(L/(1+L)); % funzione di sensitività complementare
S = minreal(1/(1+L)); % funzione di sensitività

% 1.1) Risposta a w(t)
figure(5); 
Y_w_test = W_test*F; 
[y_step_W, t_step_W] = step(Y_w_test, T_simulation);
plot(t_step_W, y_step_W, 'b');
grid on, zoom on, hold on;
% 1.2) vincolo sovraelongazione
xval_Sovraelungazione = [0, T_simulation, T_simulation, 0];
yval_Sovraelungazione = W_test*[1+S_100_spec, 1+S_100_spec, 1+2*S_100_spec, 1+2*S_100_spec];
patch(xval_Sovraelungazione, yval_Sovraelungazione, 'r','FaceAlpha',0.3,'EdgeAlpha',0.5);
ylim([W_test*0.9,W_test*1.1]);
xlim([0, 2]);
% 1.3) vincolo tempo di assestamento all'5%
LV = abs(evalfr(W_test*F,0)); % valore limite gradino: W*F(0)
xval_Tempo_assestamento = [T_a5_spec,T_simulation,T_simulation,T_a5_spec];
yval_up_Tempo_assestamento = LV*[1+T_a5_spec_val, 1+T_a5_spec_val, 1+1.5*T_a5_spec_val, 1+1.5*T_a5_spec_val];
yval_bottom_Tempo_assestamento = LV*[1-T_a5_spec_val, 1-T_a5_spec_val, 1-1.5*T_a5_spec_val, 1-1.5*T_a5_spec_val];
patch(xval_Tempo_assestamento, yval_bottom_Tempo_assestamento,'g','FaceAlpha',0.1,'EdgeAlpha',0.1);
patch(xval_Tempo_assestamento, yval_up_Tempo_assestamento,'g','FaceAlpha',0.1,'EdgeAlpha',0.1);

Legend_step = ["Risposta al gradino"; "Vincolo sovraelongazione"; "Vincolo tempo di assestamento"];
legend(Legend_step);

% 2.1) Risposta a d(t)
figure(7);
y_d = lsim(S,D_test,time_d); % y_d(t) = L[S(s)*L(d(t))]^(-1)
hold on, grid on, zoom on;
plot(time_d, y_d, 'b')
plot(time_d, D_test, 'm')
legend('y_d(t)','d(t)');

% 3.1) Risposta a n(t)
figure(8);
y_n = lsim(-F,N_test,time_n); % y_n(t) = L[-F(s)*L(n(t))]^(-1)
grid on, zoom on, hold on;
plot(time_n, y_n, 'b');
plot(time_n, N_test, 'm');
legend('y_n(t)','n(t)');

% 4.1) Risposta totale
figure(9);
T_simulation = 21;
res_time = 0:5e-5:T_simulation;

D_test = 0.1*(  sin(0.025*1*res_time) + ...
                sin(0.025*2*res_time) + ...
                sin(0.025*3*res_time) + ...
                sin(0.025*4*res_time));

N_test = 0.6*(  sin(1e3*1*res_time) + ...
                sin(1e3*2*res_time) + ...
                sin(1e3*3*res_time) + ...
                sin(1e3*4*res_time));

[y_step_W, t_step_W] = step(Y_w_test, res_time);
y_d = lsim(S, D_test, res_time);
y_n = lsim(-F, N_test, res_time);

y_tot = y_step_W + y_d + y_n;

grid on, zoom on, hold on;
plot(res_time, y_tot);
% 1.2) vincolo sovraelongazione
patch(xval_Sovraelungazione, yval_Sovraelungazione, 'r','FaceAlpha',0.3,'EdgeAlpha',0.5);
ylim([W_test*0.9,W_test*1.1]);
xlim([0, 2]);
% 1.3) vincolo tempo di assestamento all'5%
patch(xval_Tempo_assestamento, yval_bottom_Tempo_assestamento,'g','FaceAlpha',0.1,'EdgeAlpha',0.5);
patch(xval_Tempo_assestamento, yval_up_Tempo_assestamento,'g','FaceAlpha',0.1,'EdgeAlpha',0.1);
Legend_step = ["Risposta al gradino con disturbi"; "Vincolo sovraelungazione"; "Vincolo tempo assestamento"];
legend(Legend_step);

%% Animazione
ll = 0.6;

xx=-y_tot'; % segno meno per far girare le pale in senso orario per rimanere conformi al disegno
pivotPoint = [2,2];
radius = .1; %of the bob

position_1 = pivotPoint - (ll*...
                     [-sin(xx(1,1)) cos(xx(1,1))]);
position_2 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(90)) cos(xx(1,1)+deg2rad(90))]);
position_3 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(180)) cos(xx(1,1)+deg2rad(180))]);
position_4 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(270)) cos(xx(1,1)+deg2rad(270))]);
position_5 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(45)) cos(xx(1,1)+deg2rad(45))]);
position_6 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(135)) cos(xx(1,1)+deg2rad(135))]);
position_7 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(225)) cos(xx(1,1)+deg2rad(225))]);
position_8 = pivotPoint - (ll*...
                     [-sin(xx(1,1)+deg2rad(315)) cos(xx(1,1)+deg2rad(315))]);





 
rectHandle_1 = rectangle('Position',[(position_1 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_2 = rectangle('Position',[(position_2 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_3 = rectangle('Position',[(position_3 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_4 = rectangle('Position',[(position_4 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_5 = rectangle('Position',[(position_5 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_6 = rectangle('Position',[(position_6 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_7 = rectangle('Position',[(position_7 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob
rectHandle_8 = rectangle('Position',[(position_8 - radius/2) radius radius],...
                          'Curvature',[1,1],'FaceColor','r'); %Pendulum bob




lineHandle_1 = line([pivotPoint(1) position_1(1)],...
    [pivotPoint(2) position_1(2)], 'LineWidth',2); %pendulum rod
lineHandle_2 = line([pivotPoint(1) position_2(1)],...
    [pivotPoint(2) position_2(2)], 'LineWidth',2); %pendulum rod
lineHandle_3 = line([pivotPoint(1) position_3(1)],...
    [pivotPoint(2) position_3(2)], 'LineWidth',2); %pendulum rod
lineHandle_4 = line([pivotPoint(1) position_4(1)],...
    [pivotPoint(2) position_4(2)], 'LineWidth',2); %pendulum rod
lineHandle_5 = line([pivotPoint(1) position_5(1)],...
    [pivotPoint(2) position_5(2)], 'LineWidth',2); %pendulum rod
lineHandle_6 = line([pivotPoint(1) position_6(1)],...
    [pivotPoint(2) position_6(2)], 'LineWidth',2); %pendulum rod
lineHandle_7 = line([pivotPoint(1) position_7(1)],...
    [pivotPoint(2) position_7(2)], 'LineWidth',2); %pendulum rod
lineHandle_8 = line([pivotPoint(1) position_8(1)],...
    [pivotPoint(2) position_8(2)], 'LineWidth',2); %pendulum rod




axis equal
%Run simulation, all calculations are performed in cylindrical coordinates
for tt=1:length(res_time)
    
    grid on
    title("Rotores");
 
    position_1 = pivotPoint - (ll*...
                     [-sin(xx(1,tt)) cos(xx(1,tt))]);
    position_2 = pivotPoint - (ll*...
                     [-sin(xx(1,tt)+deg2rad(90)) cos(xx(1,tt)+deg2rad(90))]);
    position_3 = pivotPoint - (ll*...
                         [-sin(xx(1,tt)+deg2rad(180)) cos(xx(1,tt)+deg2rad(180))]);
    position_4 = pivotPoint - (ll*...
                     [-sin(xx(1,tt)+deg2rad(270)) cos(xx(1,tt)+deg2rad(270))]);
    position_5 = pivotPoint - (ll*...
                     [-sin(xx(1,tt)+deg2rad(45)) cos(xx(1,tt)+deg2rad(45))]);
    position_6 = pivotPoint - (ll*...
                         [-sin(xx(1,tt)+deg2rad(135)) cos(xx(1,tt)+deg2rad(135))]);
    position_7 = pivotPoint - (ll*...
                         [-sin(xx(1,tt)+deg2rad(225)) cos(xx(1,tt)+deg2rad(225))]);
    position_8 = pivotPoint - (ll*...
                     [-sin(xx(1,tt)+deg2rad(315)) cos(xx(1,tt)+deg2rad(315))]);





 
    %Update figure with new position info
    set(rectHandle_1,'Position',[(position_1 - radius/2) radius radius]);
    set(lineHandle_1,'XData',[pivotPoint(1) position_1(1)],'YData',...
        [pivotPoint(2) position_1(2)]);

    set(rectHandle_2,'Position',[(position_2 - radius/2) radius radius]);
    set(lineHandle_2,'XData',[pivotPoint(1) position_2(1)],'YData',...
        [pivotPoint(2) position_2(2)]);

    set(rectHandle_3,'Position',[(position_3 - radius/2) radius radius]);
    set(lineHandle_3,'XData',[pivotPoint(1) position_3(1)],'YData',...
        [pivotPoint(2) position_3(2)]);

    set(rectHandle_4,'Position',[(position_4 - radius/2) radius radius]);
    set(lineHandle_4,'XData',[pivotPoint(1) position_4(1)],'YData',...
        [pivotPoint(2) position_4(2)]);

    set(rectHandle_5,'Position',[(position_5 - radius/2) radius radius]);
    set(lineHandle_5,'XData',[pivotPoint(1) position_5(1)],'YData',...
        [pivotPoint(2) position_5(2)]);

    set(rectHandle_6,'Position',[(position_6 - radius/2) radius radius]);
    set(lineHandle_6,'XData',[pivotPoint(1) position_6(1)],'YData',...
        [pivotPoint(2) position_6(2)]);

    set(rectHandle_7,'Position',[(position_7 - radius/2) radius radius]);
    set(lineHandle_7,'XData',[pivotPoint(1) position_7(1)],'YData',...
        [pivotPoint(2) position_7(2)]);

    set(rectHandle_8,'Position',[(position_8 - radius/2) radius radius]);
    set(lineHandle_8,'XData',[pivotPoint(1) position_8(1)],'YData',...
        [pivotPoint(2) position_8(2)]);


    axesHandle = gca;
xlim(axesHandle, [(pivotPoint(1) - ll - radius ) ...
                            (pivotPoint(1) + ll + radius )] );
ylim(axesHandle, [(pivotPoint(2) - ll - radius) ...
                            (pivotPoint(2) +ll + radius)] );
 drawnow; %Forces MATLAB to render the pendulum
 if tt==1
     pause(0.5)
 else
    pause(0.05) 
 end
end